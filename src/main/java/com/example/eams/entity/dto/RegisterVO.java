package com.example.eams.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RegisterVO {
    private Long id;

    private String usercode;

    private String role;
}