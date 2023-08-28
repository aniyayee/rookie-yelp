package com.rookie.controller.yelp;


import com.rookie.common.core.base.BaseController;
import com.rookie.common.core.dto.ResponseDTO;
import com.rookie.common.core.page.PageDTO;
import com.rookie.domain.shop.command.AddShopCommand;
import com.rookie.domain.shop.command.UpdateShopCommand;
import com.rookie.domain.shop.db.ShopService;
import com.rookie.domain.shop.dto.ShopDTO;
import com.rookie.domain.shop.query.ShopQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @ApiOperation("Query shop List")
    @PostMapping("/list")
    public ResponseDTO<PageDTO<ShopDTO>> list(ShopQuery query) {
        PageDTO<ShopDTO> shopDTO = shopService.getShopList(query);
        return ResponseDTO.ok(shopDTO);
    }

    @ApiOperation("Query shop By shopId")
    @GetMapping("/getInfo/{shopId}")
    public ResponseDTO<ShopDTO> getInfo(@PathVariable @NotNull @Positive Long shopId) {
        return ResponseDTO.ok(shopService.getShopInfo(shopId));
    }

    @ApiOperation("Add shop")
    @PostMapping("/add")
    public ResponseDTO<Void> add(@Valid @RequestBody AddShopCommand command) {
        shopService.addShop(command);
        return ResponseDTO.ok();
    }

    @ApiOperation("Edit shop")
    @PostMapping("/edit")
    public ResponseDTO<Void> edit(@Valid @RequestBody UpdateShopCommand command) {
        shopService.updateShop(command);
        return ResponseDTO.ok();
    }

    @ApiOperation("Delete shop")
    @GetMapping("/delete/{shopId}")
    public ResponseDTO<Void> deleteById(@PathVariable @NotNull @Positive Long shopId) {
        shopService.deleteById(shopId);
        return ResponseDTO.ok();
    }
}
