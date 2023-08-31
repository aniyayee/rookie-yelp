package com.rookie.domain.blog.query;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rookie.common.core.page.AbstractPageQuery;
import com.rookie.domain.blog.db.BlogEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yayee
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BlogQuery extends AbstractPageQuery<BlogEntity> {

    @ApiModelProperty("商户ID")
    private Long shopId;

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("博客标题")
    private String title;

    @Override
    public QueryWrapper<BlogEntity> addQueryCondition() {
        QueryWrapper<BlogEntity> queryWrapper = new QueryWrapper<BlogEntity>()
            .eq(ObjectUtil.isNotNull(shopId), "shop_id", shopId)
            .eq(ObjectUtil.isNotNull(userId), "user_id", userId)
            .like(StrUtil.isNotBlank(title), "title", title);
        return queryWrapper;
    }
}
