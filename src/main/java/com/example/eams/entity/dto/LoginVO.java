package com.example.eams.entity.dto;

import lombok.Data;

@Data
public class LoginVO {
    // 外层属性：对应 JSON 里的 token
    private String token;

    // 外层属性：对应 JSON 里的 user 对象
    private UserInfo user;

    // 1. 加上 @Data 为内部类生成 get/set 方法
    // 2. 改为 public，允许 Service 层实例化它
    // 3. 类名首字母大写 UserInfo
    @Data
    public static class UserInfo {
        private Long id;
        private String usercode;
        private String role;
    }
}