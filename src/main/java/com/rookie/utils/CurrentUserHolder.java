package com.rookie.utils;

import cn.hutool.core.util.ObjectUtil;
import com.rookie.domain.user.dto.SysUserDTO;

/**
 * @author yayee
 */
public class CurrentUserHolder {

    private static final ThreadLocal<SysUserDTO> THREAD_LOCAL = new ThreadLocal<>();

    public static void saveCurrentUser(SysUserDTO dto) {
        if (ObjectUtil.isNotEmpty(dto)) {
            THREAD_LOCAL.set(dto);
        }
    }

    public static SysUserDTO getCurrentUser() {
        return THREAD_LOCAL.get();
    }

    public static void removeCurrentUser() {
        THREAD_LOCAL.remove();
    }
}
