package com.example.mall.api.param;

import lombok.Data;

@Data
public class SaveCartItemParam {
    private Integer goodsCount;

    private Long goodsId;
}
