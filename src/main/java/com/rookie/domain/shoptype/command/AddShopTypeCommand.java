package com.rookie.domain.shoptype.command;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * @author yayee
 */
@Data
public class AddShopTypeCommand {

    @ApiModelProperty("类型名称")
    @NotBlank(message = "could not be empty")
    @Size(max = 32, message = "length cannot exceed 64 characters")
    private String name;

    @ApiModelProperty("图标")
    private String icon;

    @ApiModelProperty("顺序")
    private Integer sort;
}
