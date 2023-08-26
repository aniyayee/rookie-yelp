package com.rookie.domain.shop.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yayee
 */
@Data
public class ShopDTO {

    @ApiModelProperty("商铺ID")
    private Long id;

    @ApiModelProperty("商铺名称")
    private String name;

    @ApiModelProperty("商铺类型ID")
    private Long typeId;

    @ApiModelProperty("商铺图片，多个图片以','隔开")
    private String images;

    @ApiModelProperty("商圈，例如陆家嘴")
    private String area;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("经度")
    private Double x;

    @ApiModelProperty("维度")
    private Double y;

    @ApiModelProperty("均价，取整数")
    private Long avgPrice;

    @ApiModelProperty("销量")
    private Integer sold;

    @ApiModelProperty("评论数量")
    private Integer comments;

    @ApiModelProperty("评分，1~5分，乘10保存，避免小数")
    private Integer score;

    @ApiModelProperty("营业时间，例如 10:00-22:00")
    private String openHours;
}
