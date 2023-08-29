package com.rookie.domain.shoptype.command;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yayee
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UpdateShopTypeCommand extends AddShopTypeCommand {

    @ApiModelProperty("商铺类型ID")
    @NotNull(message = "could not be null")
    @Positive
    private Long id;
}
