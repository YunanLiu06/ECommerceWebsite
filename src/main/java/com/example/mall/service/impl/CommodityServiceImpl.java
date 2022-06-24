package com.example.mall.service.impl;

import com.example.mall.api.vo.SearchCommodityVO;
import com.example.mall.common.MallException;
import com.example.mall.common.ServiceResultEnum;
import com.example.mall.dao.CommodityMapper;
import com.example.mall.entity.Commodity;
import com.example.mall.service.CommodityService;
import com.example.mall.util.PageQueryUtil;
import com.example.mall.util.PageResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public PageResult searchCommodity(PageQueryUtil pageUtil) {
        List<Commodity> commodityList = commodityMapper.findCommodityByGoodsCategoryIdAndGoodsSellStatusAndGoodsNameLikeAndGoodsIntroLike(
                (Long) pageUtil.get("commodityCategoryId"),
                (byte) pageUtil.get("commoditySellStatus"),
                (String) pageUtil.get("keyword"),
                (String) pageUtil.get("keyword"));
        int total = commodityList.size();
        List<SearchCommodityVO> searchCommodityVOS = new ArrayList<>();
        if (!CollectionUtils.isEmpty(commodityList)) {
            //copy corresponding params of commodityList to searchCommodityVOS
            for (Commodity commodity : commodityList) {
                SearchCommodityVO searchCommodityVO = new SearchCommodityVO();
                BeanUtils.copyProperties(commodity, searchCommodityVO);
                searchCommodityVOS.add(searchCommodityVO);
            }
            for (SearchCommodityVO searchCommodityVO : searchCommodityVOS) {
                String commodityName = searchCommodityVO.getGoodsName();
                String commodityIntro = searchCommodityVO.getGoodsIntro();

                if (commodityName.length() > 20) {
                    commodityName = commodityName.substring(0,28) + "...";
                    searchCommodityVO.setGoodsName(commodityName);
                }
                if (commodityIntro.length() > 20) {
                    commodityIntro = commodityIntro.substring(0,28) + "...";
                    searchCommodityVO.setGoodsName(commodityIntro);
                }
            }
        }
        PageResult pageResult = new PageResult(searchCommodityVOS, total, pageUtil.getLimit(), pageUtil.getPage());

        return pageResult;
    }
}
