package com.rookie.controller.yelp;


import com.rookie.common.core.base.BaseController;
import com.rookie.common.core.dto.ResponseDTO;
import com.rookie.domain.shoptype.db.ShopTypeService;
import com.rookie.domain.shoptype.dto.ShopTypeDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 商铺类型表 前端控制器
 * </p>
 *
 * @author yayee
 */
@Api(value = "ShopType Interfaces", tags = "ShopType Interfaces")
@RestController
@RequestMapping("/biz/shop-type")
public class ShopTypeController extends BaseController {

    @Resource
    private ShopTypeService shopTypeService;

    @ApiOperation("Query shopType List")
    @GetMapping("/list")
    public ResponseDTO<List<ShopTypeDTO>> list() {
        return ResponseDTO.ok(shopTypeService.findList());
    }
}

