package com.example.eams.entity.dto;


import lombok.Data;
/*
前端发给后端的请求体，内容为注册用户的账号id和密码
*/
@Data
public class RegisterDTO {
    private String usercode;
    private String password;

}
