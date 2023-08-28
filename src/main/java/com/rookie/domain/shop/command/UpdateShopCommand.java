package com.rookie.domain.shop.command;

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
public class UpdateShopCommand extends AddShopCommand {

    @ApiModelProperty("商铺ID")
    @NotNull(message = "could not be null")
    @Positive
    private Long id;
}
