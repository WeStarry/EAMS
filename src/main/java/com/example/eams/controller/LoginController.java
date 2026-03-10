package com.example.eams.controller;

import com.example.eams.common.Result;
import com.example.eams.entity.dto.LoginDTO;
import com.example.eams.entity.dto.LoginVO;
import com.example.eams.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result<LoginVO> login(@RequestBody LoginDTO loginDTO) {
        // 调用 Service 层鉴权并获取数据
        LoginVO loginVO = userService.login(loginDTO);

        // 序列化输出给前端。由于你原来的 success 方法写死了 msg="操作成功"，
        // 这一步直接用即可，前端主要依赖 code === 200 和 token 来放行逻辑。
        return Result.success(loginVO);
    }
}