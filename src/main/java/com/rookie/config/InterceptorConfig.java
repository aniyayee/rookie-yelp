package com.rookie.config;

import javax.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author yayee
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 登录拦截器
        registry.addInterceptor(new LoginInterceptor())
            .excludePathPatterns(
                "/captcha/**",
                "/login",
                "/doc.html/**",
                "/webjars/**",
                "/favicon.*",
                "/swagger-resources/**",
                "/v3/api-docs"
            ).order(1);
        // token刷新的拦截器
        registry.addInterceptor(new RefreshTokenInterceptor(stringRedisTemplate))
            .addPathPatterns("/**")
            .order(0);
    }
}
