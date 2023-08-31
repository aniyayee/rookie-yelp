package com.rookie.domain.blog.db;

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
 * 博客信息表
 * </p>
 *
 * @author yayee
 */
@Getter
@Setter
@TableName("biz_blog")
@ApiModel(value = "BlogEntity对象", description = "博客信息表")
public class BlogEntity extends BaseEntity<BlogEntity> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("博客ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("商户ID")
    @TableField("shop_id")
    private Long shopId;

    @ApiModelProperty("用户ID")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty("博客标题")
    @TableField("title")
    private String title;

    @ApiModelProperty("探店的照片（最多9张 多张以\",\"隔开）")
    @TableField("images")
    private String images;

    @ApiModelProperty("博客内容")
    @TableField("content")
    private String content;

    @ApiModelProperty("点赞数")
    @TableField("likes")
    private Integer likes;

    @ApiModelProperty("评论数")
    @TableField("comments")
    private Integer comments;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
