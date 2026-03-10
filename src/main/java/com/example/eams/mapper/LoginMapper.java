package com.example.eams.mapper;

import com.example.eams.entity.User;


public interface LoginMapper {

    /*用户注册数据保存到数据库*/
    int registerUser(User user);

    /**
     * 根据 usercode 查询单条完整的用户信息
     * @param usercode 前端传来的登录账号
     * @return 数据库映射的 User 实体对象 (查不到则返回 null)
     */
    User selectByUsercode(String usercode);
}
