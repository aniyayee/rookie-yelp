package com.rookie.config;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.rookie.common.constants.RedisConstants;
import com.rookie.domain.user.dto.SysUserDTO;
import com.rookie.utils.CurrentUserHolder;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author yayee
 */
public class RefreshTokenInterceptor implements HandlerInterceptor {

    private final StringRedisTemplate stringRedisTemplate;

    public RefreshTokenInterceptor(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
        // 1.获取请求头中的token
        String token = request.getHeader("authorization");
        if (StrUtil.isBlank(token)) {
            return true;
        }
        // 2.基于token获取redis中的用户
        String tokenKey = RedisConstants.LOGIN_USER_KEY + token;
        Map<Object, Object> userMap = stringRedisTemplate.opsForHash().entries(tokenKey);
        if (ObjectUtil.isEmpty(userMap)) {
            return true;
        }
        // 3.将查询到的hash数据转为UserDTO
        SysUserDTO dto = BeanUtil.fillBeanWithMap(userMap, new SysUserDTO(), false);
        // 4.保存用户信息到 ThreadLocal
        CurrentUserHolder.saveCurrentUser(dto);
        // 5.刷新token过期时间
        stringRedisTemplate.expire(tokenKey, RedisConstants.LOGIN_USER_TTL, TimeUnit.SECONDS);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
        throws Exception {
        CurrentUserHolder.removeCurrentUser();
    }
}
