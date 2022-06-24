package com.example.mall.api;

import com.example.mall.api.vo.CommodityDetailVO;
import com.example.mall.api.vo.SearchCommodityVO;
import com.example.mall.common.Constants;
import com.example.mall.common.MallException;
import com.example.mall.common.ServiceResultEnum;
import com.example.mall.config.annotation.TokenToUser;
import com.example.mall.entity.Commodity;
import com.example.mall.entity.User;
import com.example.mall.service.CommodityService;
import com.example.mall.util.PageQueryUtil;
import com.example.mall.util.PageResult;
import com.example.mall.util.Result;
import com.example.mall.util.ResultGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/search")
    public Result<PageResult<List<SearchCommodityVO>>> search(@RequestParam(required = false) String keyword,
                                                              @RequestParam(required = false) Long commodityCategoryId,
                                                              @RequestParam(required = false) String orderBy,
                                                              @RequestParam(required = false) Integer pageNumber,
                                                              @TokenToUser User user)
    {
        log.info("goods search api,keyword={},goodsCategoryId={},orderBy={},pageNumber={},userId={}",
                keyword,
                commodityCategoryId,
                orderBy,
                pageNumber,
                user.getUserId());

        Map params = new HashMap(8);

        if(commodityCategoryId == null && StringUtils.isEmpty(keyword)) {
            MallException.fail("Wrong searching param");
        }

        if(pageNumber == null || pageNumber < 1) {
            pageNumber = 1;
        }

        params.put("commodityCategoryId", commodityCategoryId);
        params.put("page", pageNumber);
        params.put("limit", Constants.GOODS_SEARCH_PAGE_LIMIT);

        if(!StringUtils.isEmpty(keyword)){
            params.put("keyword", keyword);
        }

        if(!StringUtils.isEmpty(orderBy)){
            params.put("orderBy", orderBy);
        }

        params.put("commoditySellStatus", Constants.SELL_STATUS_UP);

        PageQueryUtil pageQueryUtil = new PageQueryUtil(params);

        return ResultGenerator.genSuccessResult(commodityService.searchCommodity(pageQueryUtil));

    }

}
