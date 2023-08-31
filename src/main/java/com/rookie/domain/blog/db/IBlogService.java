package com.rookie.domain.blog.db;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rookie.common.core.page.PageDTO;
import com.rookie.domain.blog.command.AddBlogCommand;
import com.rookie.domain.blog.command.UpdateBlogCommand;
import com.rookie.domain.blog.dto.BlogDTO;
import com.rookie.domain.blog.query.BlogQuery;

/**
 * <p>
 * 博客信息表 服务类
 * </p>
 *
 * @author yayee
 */
public interface IBlogService extends IService<BlogEntity> {

    PageDTO<BlogDTO> getBlogList(BlogQuery query);

    BlogDTO getBlogInfo(Long id);

    void addBlog(AddBlogCommand command);

    void updateBlog(UpdateBlogCommand command);

    void deleteById(Long id);

    void likeBlog(Long id);
}
