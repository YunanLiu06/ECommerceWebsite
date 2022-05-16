package com.example.mall.api.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserRegisterParam {

    @NotEmpty(message = "Login Name cannot be null")
    private String loginName;

    @NotEmpty(message = "Password cannot be null")
    private String password;
}
