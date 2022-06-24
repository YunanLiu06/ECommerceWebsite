package com.example.mall.dao;

import com.example.mall.entity.Commodity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommodityMapper extends CrudRepository<Commodity, Long> {
    Commodity findCommodityByGoodsId(Long goodsId);
    List<Commodity> findCommodityByGoodsCategoryIdAndGoodsSellStatusAndGoodsNameLikeAndGoodsIntroLike(Long categoryId,
                                                                                                      Byte sellStatus,
                                                                                                      String nameLike,
                                                                                                      String introLike);
}
