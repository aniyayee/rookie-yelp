package com.rookie.common.constants;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author yayee
 */
@Getter
@AllArgsConstructor
public enum ErrorCode {

    /**
     * 成功
     */
    SUCCESS("200", "success"),
    /**
     * 失败
     */
    FAIL("500", "failed"),

    /**
     * HTTP 状态码
     */
    HTTP_STATUS_200("200", "ok"), HTTP_STATUS_400("400", "request error"), HTTP_STATUS_401("401",
        "no authentication"), HTTP_STATUS_403("403", "no authorities"), HTTP_STATUS_500("500", "server error");

    public static final List<ErrorCode> HTTP_STATUS_ALL = Collections.unmodifiableList(
        Arrays.asList(HTTP_STATUS_200, HTTP_STATUS_400, HTTP_STATUS_401, HTTP_STATUS_403, HTTP_STATUS_500));

    /**
     * response code
     */
    private final String code;

    /**
     * msg
     */
    private final String msg;

    @Getter
    @AllArgsConstructor
    public enum Business {

        // ------------------------------- USER ---------------------------------------------
        USER_NAME_IS_NOT_UNIQUE(1001, "用户名已被其他用户占用"),
        PHONE_NUMBER_IS_NOT_UNIQUE(1002, "手机号码已存在"),
        SAME_NEW_AND_OLD_PASSWORDS(1003, "新旧密码相同"),
        USER_PASSWORD_IS_NOT_CORRECT(1004, "用户密码错误"),
        MOBILE_PHONE_NUMBER_FORMAT_ERROR(1005, "手机号格式错误"),

        // ------------------------------- ROLE ---------------------------------------------
        ROLE_NAME_IS_NOT_UNIQUE(2001, "角色名称已存在");

        private final int value;
        private final String msg;
    }
}
