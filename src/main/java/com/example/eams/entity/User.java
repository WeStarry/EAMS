package com.example.eams.entity;


import lombok.Data;

@Data
public class User {
    private Long id;
    private String usercode;
    private String password;
    private String role;
}
