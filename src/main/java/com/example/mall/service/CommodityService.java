package com.example.mall.service;

import com.example.mall.entity.Commodity;
import com.example.mall.util.PageQueryUtil;
import com.example.mall.util.PageResult;

public interface CommodityService {
    Commodity getGoodsById(Long id);

    PageResult searchCommodity(PageQueryUtil pageUtil);
}
