package com.example.mall.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Data
public class UserToken {
    @Id
    @GeneratedValue
    private Long userId;

    private String token;

    private Date updateTime;

    private Date expireTime;
}
