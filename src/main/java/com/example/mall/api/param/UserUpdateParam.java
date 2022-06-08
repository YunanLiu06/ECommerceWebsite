package com.example.mall.api.param;

import lombok.Data;

@Data
public class UserUpdateParam {

    private String nickname;

    private String passWord;

    private String introduceSign;
}
