package com.rookie.domain.shop.query;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rookie.common.core.page.AbstractPageQuery;
import com.rookie.domain.shop.db.ShopEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yayee
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ShopQuery extends AbstractPageQuery<ShopEntity> {

    @ApiModelProperty("商铺名称")
    private String name;

    @ApiModelProperty("商铺类型ID")
    private Long typeId;

    @Override
    public QueryWrapper<ShopEntity> addQueryCondition() {
        QueryWrapper<ShopEntity> queryWrapper = new QueryWrapper<ShopEntity>()
            .eq(ObjectUtil.isNotNull(typeId), "type_id", typeId)
            .like(StrUtil.isNotBlank(name), "name", name);
        return queryWrapper;
    }
}
