package com.example.mall.api.vo;


import lombok.Data;

@Data
public class CommodityDetailVO {

    private Long goodsId;

    private String goodsName;

    private String goodsIntro;

    private String goodsCoverImg;

    private Integer sellingPrice;

    private String tag;

    private String[] goodsCarouselList;

    private Integer originalPrice;

    private String goodsDetailContent;
}
