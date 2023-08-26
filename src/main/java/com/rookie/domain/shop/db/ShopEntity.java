package com.rookie.domain.shop.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rookie.common.core.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 商铺信息表
 * </p>
 *
 * @author yayee
 */
@Getter
@Setter
@TableName("biz_shop")
@ApiModel(value = "ShopEntity对象", description = "商铺信息表")
public class ShopEntity extends BaseEntity<ShopEntity> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("商铺ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("商铺名称")
    @TableField("`name`")
    private String name;

    @ApiModelProperty("商铺类型ID")
    @TableField("type_id")
    private Long typeId;

    @ApiModelProperty("商铺图片，多个图片以','隔开")
    @TableField("images")
    private String images;

    @ApiModelProperty("商圈，例如陆家嘴")
    @TableField("area")
    private String area;

    @ApiModelProperty("地址")
    @TableField("address")
    private String address;

    @ApiModelProperty("经度")
    @TableField("x")
    private Double x;

    @ApiModelProperty("维度")
    @TableField("y")
    private Double y;

    @ApiModelProperty("均价，取整数")
    @TableField("avg_price")
    private Long avgPrice;

    @ApiModelProperty("销量")
    @TableField("sold")
    private Integer sold;

    @ApiModelProperty("评论数量")
    @TableField("comments")
    private Integer comments;

    @ApiModelProperty("评分，1~5分，乘10保存，避免小数")
    @TableField("score")
    private Integer score;

    @ApiModelProperty("营业时间，例如 10:00-22:00")
    @TableField("open_hours")
    private String openHours;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
