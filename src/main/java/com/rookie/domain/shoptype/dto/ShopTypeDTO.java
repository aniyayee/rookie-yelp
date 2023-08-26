package com.rookie.domain.shoptype.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yayee
 */
@Data
public class ShopTypeDTO {

    @ApiModelProperty("商铺类型ID")
    private Long id;

    @ApiModelProperty("类型名称")
    private String name;

    @ApiModelProperty("图标")
    private String icon;

    @ApiModelProperty("顺序")
    private Integer sort;
}
