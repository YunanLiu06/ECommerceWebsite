package com.example.mall.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Data
public class User {
    @Id
    @GeneratedValue
    private Long userId;

    private String nickName;

    private String loginName;

    private String password;

    private String introduceSign;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}
