package com.example.mall.api.vo;

import lombok.Data;

@Data
public class SearchCommodityVO {

    private Long goodsId;

    private String goodsName;

    private String goodsIntro;

    private String goodsCoverImg;

    private Integer sellingPrice;
}
