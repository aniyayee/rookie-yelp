package com.rookie.domain.blog.dto;

import cn.hutool.core.bean.BeanUtil;
import com.rookie.domain.blog.db.BlogEntity;
import com.rookie.domain.shop.dto.ShopDTO;
import com.rookie.domain.user.dto.SysUserDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yayee
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BlogDTO {

    public BlogDTO(BlogEntity entity, SysUserDTO userDTO, ShopDTO shopDTO) {
        if (entity != null) {
            BeanUtil.copyProperties(entity, this);
        }
        if (userDTO != null) {
            this.nickname = userDTO.getNickname();
        }
        if (shopDTO != null) {
            this.shopName = shopDTO.getName();
        }

    }

    @ApiModelProperty("博客ID")
    private Long id;

    @ApiModelProperty("商户ID")
    private Long shopId;

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("博客标题")
    private String title;

    @ApiModelProperty("探店的照片（最多9张 多张以\",\"隔开）")
    private String images;

    @ApiModelProperty("博客内容")
    private String content;

    @ApiModelProperty("点赞数")
    private Integer likes;

    @ApiModelProperty("评论数")
    private Integer comments;

    @ApiModelProperty("商铺名称")
    private String shopName;

    @ApiModelProperty("用户昵称")
    private String nickname;

    /**
     * 点赞（0代表未点赞 1代表点赞）
     */
    private int isLike;
}
