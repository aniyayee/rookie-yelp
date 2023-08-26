package com.rookie.domain.shoptype.db;

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
 * 商铺类型表
 * </p>
 *
 * @author yayee
 */
@Getter
@Setter
@TableName("biz_shop_type")
@ApiModel(value = "ShopTypeEntity对象", description = "商铺类型表")
public class ShopTypeEntity extends BaseEntity<ShopTypeEntity> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("商铺类型ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("类型名称")
    @TableField("`name`")
    private String name;

    @ApiModelProperty("图标")
    @TableField("icon")
    private String icon;

    @ApiModelProperty("顺序")
    @TableField("sort")
    private Integer sort;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
