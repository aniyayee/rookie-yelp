package com.rookie.domain.blog.command;

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
public class AddBlogCommand {

    @ApiModelProperty("商户ID")
    @NotNull(message = "could not be null")
    @Positive
    private Long shopId;

    @ApiModelProperty("博客标题")
    @NotBlank(message = "could not be empty")
    @Size(max = 128, message = "length cannot exceed 128 characters")
    private String title;

    @ApiModelProperty("探店的照片（最多9张 多张以\",\"隔开）")
    private String images;

    @ApiModelProperty("博客内容")
    @NotBlank(message = "could not be empty")
    @Size(max = 1024, message = "length cannot exceed 1024 characters")
    private String content;

    @ApiModelProperty("点赞数")
    private Integer likes;

    @ApiModelProperty("评论数")
    private Integer comments;
}
