package com.example.mall.service.impl;

import com.example.mall.common.MallException;
import com.example.mall.common.ServiceResultEnum;
import com.example.mall.dao.CommodityMapper;
import com.example.mall.entity.Commodity;
import com.example.mall.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommodityServiceImpl implements CommodityService {

    @Autowired
    private CommodityMapper commodityMapper;

    @Override
    public Commodity getGoodsById(Long id) {
        Commodity commodity = commodityMapper.findCommodityByGoodsId(id);
        if(commodity == null) {
            MallException.fail(ServiceResultEnum.GOODS_NOT_EXIST.getResult());
        }
        return commodity;
    }
}
