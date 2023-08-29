package com.rookie.controller.yelp;


import com.rookie.common.core.base.BaseController;
import com.rookie.common.core.dto.ResponseDTO;
import com.rookie.domain.shoptype.command.AddShopTypeCommand;
import com.rookie.domain.shoptype.command.UpdateShopTypeCommand;
import com.rookie.domain.shoptype.db.ShopTypeService;
import com.rookie.domain.shoptype.dto.ShopTypeDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
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

    @ApiOperation("Query shopType By typeId")
    @GetMapping("/getInfo/{typeId}")
    public ResponseDTO<ShopTypeDTO> getInfo(@PathVariable @NotNull @Positive Long typeId) {
        return ResponseDTO.ok(shopTypeService.getShopTypeInfo(typeId));
    }

    @ApiOperation("Add shopType")
    @PostMapping("/add")
    public ResponseDTO<Void> add(@Valid @RequestBody AddShopTypeCommand command) {
        shopTypeService.addShopType(command);
        return ResponseDTO.ok();
    }

    @ApiOperation("Edit shopType")
    @PostMapping("/edit")
    public ResponseDTO<Void> edit(@Valid @RequestBody UpdateShopTypeCommand command) {
        shopTypeService.updateShopType(command);
        return ResponseDTO.ok();
    }

    @ApiOperation("Delete shopType")
    @GetMapping("/delete/{typeId}")
    public ResponseDTO<Void> deleteById(@PathVariable @NotNull @Positive Long typeId) {
        shopTypeService.deleteById(typeId);
        return ResponseDTO.ok();
    }
}

