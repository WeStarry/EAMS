package com.example.eams.service;

import com.example.eams.common.utils.JwtUtils;
import com.example.eams.entity.User;

import com.example.eams.entity.dto.LoginDTO;
import com.example.eams.entity.dto.LoginVO;
import com.example.eams.entity.dto.RegisterDTO;
import com.example.eams.entity.dto.RegisterVO;
import com.example.eams.mapper.LoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private LoginMapper loginMapper;

    public RegisterVO register(RegisterDTO registerDTO) {

        // 1. 实例化 Entity 并赋值
        User user = new User();
        user.setUsercode(registerDTO.getUsercode());
        user.setPassword(registerDTO.getPassword());
        user.setRole("user");

        // 2. 调用底层的插入方法
        // 注意：执行完这一行之后，由于我们在 XML 配置了主键回写，
        // 此时内存中的 user 对象已经被 MyBatis 自动注入了真实的数据库 ID（例如 2）！
        loginMapper.registerUser(user);

        // 3. 实例化 VO 并从 Entity 中提取数据进行响应装配
        RegisterVO registerVO = new RegisterVO();
        registerVO.setId(user.getId()); // 现在这里能准确拿到 2 了！
        registerVO.setUsercode(user.getUsercode()); // 序列化时会自动变成 "username"
        registerVO.setRole(user.getRole());

        // 4. 返回装配完成的 VO 交由 Controller 序列化
        return registerVO;
    }

    // 在 UserService 中新增登录方法
    // 返回值是我们之前严格定义好的 LoginVO，入参是前端传来的 LoginDTO
    public LoginVO login(LoginDTO loginDTO) {

        // 1. 提取前端传来的账号
        String inputUsercode = loginDTO.getUsercode();
        String inputPassword = loginDTO.getPassword();

        // 2. 调用 Mapper，去数据库里捞出完整的 User 实体对象
        User dbUser = loginMapper.selectByUsercode(inputUsercode);

        // 3. 第一道防线：非空拦截
        // 直接判断内存对象引用是否为空，这是最快、最直接的判空指令
        if (dbUser == null) {
            // 直接抛出运行时异常打断程序，交由上层统一处理
            throw new RuntimeException("用户名或密码错误");
        }

        // 4. 第二道防线：密码比对
        // 调用 Java String 类的底层 equals 方法进行精确的字符序列比对
        // 注意：这里必须保证 dbUser 不是 null 才能调用 getPassword()，
        // 我们在第 3 步已经做了防御，所以这里绝对安全，不会触发 NullPointerException
        if (!dbUser.getPassword().equals(inputPassword)) {
            // 同样直接抛出异常，注意提示语必须和上面保持一致，防止黑客探测有效账号
            throw new RuntimeException("用户名或密码错误");
        }

        // 5. 实例化准备返回给前端的 VO 对象
        LoginVO loginVO = new LoginVO();

        // 6. 实例化 VO 内部嵌套的 UserInfo 对象，并进行安全的数据转移（绝对不放密码）
        LoginVO.UserInfo userInfo = new LoginVO.UserInfo();
        // TODO: 把 dbUser 里的 id, usercode, role 提取出来，通过 set 方法塞进 userInfo 里
        userInfo.setId(dbUser.getId());
        userInfo.setUsercode(dbUser.getUsercode());
        userInfo.setRole(dbUser.getRole());
        // 7. 把组装好的 userInfo 塞进外层的 loginVO 里
        loginVO.setUser(userInfo);

        // 8. 动态生成加密的 JWT Token
        String token = JwtUtils.generateToken(dbUser.getId(), dbUser.getUsercode(), dbUser.getRole());
        loginVO.setToken(token);

        // 9. 完美返回
        return loginVO;
    }
}