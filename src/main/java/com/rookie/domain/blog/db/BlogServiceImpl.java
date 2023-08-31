package com.rookie.domain.blog.db;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rookie.common.constants.RedisConstants;
import com.rookie.common.core.page.PageDTO;
import com.rookie.common.exception.ApiException;
import com.rookie.common.exception.error.ErrorCode.Business;
import com.rookie.domain.blog.command.AddBlogCommand;
import com.rookie.domain.blog.command.UpdateBlogCommand;
import com.rookie.domain.blog.dto.BlogDTO;
import com.rookie.domain.blog.query.BlogQuery;
import com.rookie.domain.shop.db.IShopService;
import com.rookie.domain.shop.dto.ShopDTO;
import com.rookie.domain.user.db.ISysUserService;
import com.rookie.domain.user.dto.SysUserDTO;
import com.rookie.utils.CurrentUserHolder;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 博客信息表 服务实现类
 * </p>
 *
 * @author yayee
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, BlogEntity> implements IBlogService {

    @Resource
    private ISysUserService userService;

    @Resource
    private IShopService shopService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public PageDTO<BlogDTO> getBlogList(BlogQuery query) {
        Page<BlogEntity> page = this.page(query.toPage(), query.toQueryWrapper());
        List<BlogDTO> records = page.getRecords().stream().map(item -> {
                SysUserDTO userDTO = userService.queryById(item.getUserId());
                ShopDTO shopDTO = shopService.getShopInfo(item.getShopId());
                BlogDTO dto = new BlogDTO(item, userDTO, shopDTO);
                return handleBlogLike(dto);
            })
            .collect(Collectors.toList());
        return new PageDTO<>(page.getTotal(), records);
    }

    @Override
    public BlogDTO getBlogInfo(Long id) {
        BlogEntity entity = baseMapper.selectById(id);
        if (ObjectUtil.isEmpty(entity)) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, id, "博客");
        }
        SysUserDTO userDTO = userService.queryById(entity.getUserId());
        ShopDTO shopDTO = shopService.getShopInfo(entity.getShopId());
        BlogDTO dto = new BlogDTO(entity, userDTO, shopDTO);
        return handleBlogLike(dto);
    }

    @Override
    public void addBlog(AddBlogCommand command) {
        BlogEntity entity = BeanUtil.copyProperties(command, BlogEntity.class);
        entity.setUserId(CurrentUserHolder.getCurrentUser().getUserId());
        baseMapper.insert(entity);
    }

    @Override
    public void updateBlog(UpdateBlogCommand command) {
        Long id = command.getId();
        BlogEntity byId = baseMapper.selectById(id);
        if (ObjectUtil.isEmpty(byId)) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, id, "博客");
        }
        BlogEntity entity = BeanUtil.copyProperties(command, BlogEntity.class);
        baseMapper.updateById(entity);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        String key = RedisConstants.BLOG_LIKED_KEY + id;
        baseMapper.deleteById(id);
        stringRedisTemplate.delete(key);
    }

    @Transactional
    @Override
    public void likeBlog(Long id) {
        BlogEntity entity = baseMapper.selectById(id);
        if (ObjectUtil.isEmpty(entity)) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, id, "博客");
        }
        // 1.获取当前用户
        Long userId = CurrentUserHolder.getCurrentUser().getUserId();
        if ((userId == null)) {
            // 用户未登录
            return;
        }
        String userIdStr = userId.toString();
        String key = RedisConstants.BLOG_LIKED_KEY + id;
        // 2.查缓存 判读当前用户是否点赞
        Boolean isMember = stringRedisTemplate.opsForSet().isMember(key, userIdStr);
        if (BooleanUtil.isTrue(isMember)) {
            // 3.已点赞 -> 取消赞 数据库-1 redis移除
            boolean isSuccess = this.update().setSql("likes = likes - 1").eq("id", id).update();
            if (isSuccess) {
                stringRedisTemplate.opsForSet().remove(key, userIdStr);
            }
        } else {
            // 4.未点赞 -> 点赞 数据库+1 redis添加
            boolean isSuccess = this.update().setSql("likes = likes + 1").eq("id", id).update();
            if (isSuccess) {
                stringRedisTemplate.opsForSet().add(key, userIdStr);
            }
        }
    }

    private BlogDTO handleBlogLike(BlogDTO dto) {
        String key = RedisConstants.BLOG_LIKED_KEY + dto.getId();
        Long userId = CurrentUserHolder.getCurrentUser().getUserId();
        Boolean isMember = stringRedisTemplate.opsForSet().isMember(key, userId.toString());
        if (BooleanUtil.isTrue(isMember)) {
            dto.setIsLike(1);
        }
        return dto;
    }
}
