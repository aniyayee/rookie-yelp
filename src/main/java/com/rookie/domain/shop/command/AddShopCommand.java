package com.rookie.domain.shop.command;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * @author yayee
 */
@Data
public class AddShopCommand {

    @ApiModelProperty("商铺名称")
    @NotBlank(message = "could not be empty")
    @Size(max = 64, message = "length cannot exceed 64 characters")
    private String name;

    @ApiModelProperty("商铺类型ID")
    @NotNull(message = "could not be null")
    @Positive
    private Long typeId;

    @ApiModelProperty("商铺图片，多个图片以','隔开")
    @NotBlank(message = "could not be empty")
    private String images;

    @ApiModelProperty("商圈，例如陆家嘴")
    private String area;

    @ApiModelProperty("地址")
    @NotBlank(message = "could not be empty")
    private String address;

    @ApiModelProperty("经度")
    @NotNull(message = "could not be null")
    private Double x;

    @ApiModelProperty("维度")
    @NotNull(message = "could not be null")
    private Double y;

    @ApiModelProperty("均价，取整数")
    private Long avgPrice;

    @ApiModelProperty("销量")
    @NotNull(message = "could not be null")
    @Positive
    private Integer sold;

    @ApiModelProperty("评论数量")
    @NotNull(message = "could not be null")
    @Positive
    private Integer comments;

    @ApiModelProperty("评分，1~5分，乘10保存，避免小数")
    @NotNull(message = "could not be null")
    @Positive
    private Integer score;

    @ApiModelProperty("营业时间，例如 10:00-22:00")
    @NotBlank(message = "could not be empty")
    private String openHours;
}
