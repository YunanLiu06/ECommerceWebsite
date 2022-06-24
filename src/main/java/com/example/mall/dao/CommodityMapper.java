package com.example.mall.dao;

import com.example.mall.entity.Commodity;
import org.springframework.data.repository.CrudRepository;

public interface CommodityMapper extends CrudRepository<Commodity, Long> {
    Commodity findCommodityByGoodsId(Long goodsId);
}
