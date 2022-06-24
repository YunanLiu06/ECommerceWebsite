package com.example.mall.api;

import com.example.mall.api.vo.CommodityDetailVO;
import com.example.mall.common.Constants;
import com.example.mall.common.MallException;
import com.example.mall.common.ServiceResultEnum;
import com.example.mall.config.annotation.TokenToUser;
import com.example.mall.entity.Commodity;
import com.example.mall.entity.User;
import com.example.mall.service.CommodityService;
import com.example.mall.util.Result;
import com.example.mall.util.ResultGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@Slf4j
public class CommodityAPI {

    @Autowired
    private CommodityService commodityService;

    @GetMapping("/goods/detail/{commodityId}")
    public Result<CommodityDetailVO> commodityDetail
            (@PathVariable("commodityId") Long commodityId,
             @TokenToUser User user)
    {

        log.info("goods detail api,goodsId={},userId={}", commodityId, user.getUserId());
        if(commodityId < 1) {
            return ResultGenerator.genFailResult("Wrong Parameter");
        }
        Commodity commodity = commodityService.getGoodsById(commodityId);

        if(Constants.SELL_STATUS_DOWN == commodity.getGoodsSellStatus()) {
            MallException.fail(ServiceResultEnum.GOODS_PUT_DOWN.getResult());
        }
        CommodityDetailVO commodityDetailVO = new CommodityDetailVO();
        BeanUtils.copyProperties(commodity, commodityDetailVO);
        commodityDetailVO.setGoodsCarouselList(commodity.getGoodsCarousel().split(","));
        return ResultGenerator.genSuccessResult(commodityDetailVO);
    }

}
