package com.rookie.controller.yelp;


import com.rookie.common.core.base.BaseController;
import com.rookie.common.core.dto.ResponseDTO;
import com.rookie.domain.shop.db.ShopService;
import com.rookie.domain.shop.dto.ShopDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 商铺信息表 前端控制器
 * </p>
 *
 * @author yayee
 */
@Api(value = "Shop Interfaces", tags = "Shop Interfaces")
@RestController
@RequestMapping("/biz/shop")
public class ShopController extends BaseController {

    @Resource
    private ShopService shopService;

    @ApiOperation("Query shop By shopId")
    @GetMapping("/queryById/{shopId}")
    public ResponseDTO<ShopDTO> queryById(@PathVariable("shopId") Long id) {
        return ResponseDTO.ok(shopService.queryById(id));
    }
}
