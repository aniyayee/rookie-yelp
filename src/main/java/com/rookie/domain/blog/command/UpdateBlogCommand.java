package com.rookie.domain.blog.command;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yayee
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UpdateBlogCommand extends AddBlogCommand {

    @ApiModelProperty("博客ID")
    private Long id;
}
