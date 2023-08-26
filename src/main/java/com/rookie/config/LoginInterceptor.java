package com.rookie.config;

import cn.hutool.core.util.ObjectUtil;
import com.rookie.common.exception.ApiException;
import com.rookie.common.exception.error.ErrorCode;
import com.rookie.utils.CurrentUserHolder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author yayee
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
        if (ObjectUtil.isEmpty(CurrentUserHolder.getCurrentUser())) {
            throw new ApiException(ErrorCode.HTTP_STATUS_401);
        }
        return true;
    }
}
