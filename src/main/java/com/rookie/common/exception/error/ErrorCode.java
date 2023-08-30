package com.rookie.common.exception.error;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.util.Assert;

/**
 * 错误码集合
 *
 * @author yayee
 */
public enum ErrorCode implements ErrorCodeInterface {

    /**
     * 成功
     */
    SUCCESS(200, "success"),
    /**
     * 失败
     */
    FAIL(500, "failed"),

    /**
     * HTTP 状态码
     */
    HTTP_STATUS_200(200, "ok"),
    HTTP_STATUS_400(400, "request error"),
    HTTP_STATUS_401(401, "no authentication"),
    HTTP_STATUS_403(403, "no authorities"),
    HTTP_STATUS_500(500, "server error");

    public static final List<ErrorCode> HTTP_STATUS_ALL = Collections.unmodifiableList(
        Arrays.asList(HTTP_STATUS_200, HTTP_STATUS_400, HTTP_STATUS_401, HTTP_STATUS_403, HTTP_STATUS_500));

    /**
     * response code
     */
    private final int code;

    /**
     * msg
     */
    private final String msg;

    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int code() {
        return this.code;
    }

    @Override
    public String message() {
        return this.msg;
    }


    /**
     * 10000~99999 为业务逻辑 错误码 （无代码异常，代码正常流转，并返回提示给用户） 1XX01   XX是代表模块的意思 比如10101   01是Permission模块 错误码的命名最好以模块为开头
     * 比如OBJECT_NOT_FOUND前面加上COMMON = COMMON_OBJECT_NOT_FOUND
     */
    public enum Business implements ErrorCodeInterface {

        // ------------------------------- COMMON -------------------------------------------
        COMMON_OBJECT_NOT_FOUND(10001, "找不到ID为 {} 的 {}"),
        COMMON_UNSUPPORTED_OPERATION(10002, "不支持的操作"),
        COMMON_FILE_NOT_ALLOWED_TO_DOWNLOAD(10003, "文件名称({})非法，不允许下载"),

        // ------------------------------- USER ---------------------------------------------
        USER_NAME_IS_NOT_UNIQUE(10101, "用户名已被其他用户占用"),
        USER_PHONE_IS_NOT_UNIQUE(10102, "该电话号码已被其他用户占用"),
        USER_NEW_PASSWORD_IS_THE_SAME_AS_OLD(10103, "用户新密码与旧密码相同"),
        USER_PASSWORD_IS_NOT_CORRECT(10104, "用户密码错误"),
        USER_PHONE_FORMAT_ERROR(10105, "用户手机号格式错误"),
        USER_UPLOAD_FILE_FAILED(10106, "用户上传文件失败"),

        // ------------------------------- ROLE ----------------------------------------------
        ROLE_NAME_IS_NOT_UNIQUE(10201, "角色名称已存在"),

        // ----------------------------- LOGIN -----------------------------------------
        LOGIN_CAPTCHA_CODE_WRONG(10301, "验证码错误"),
        LOGIN_ERROR(10302, "登录失败：{}"),

        // ----------------------------- UPLOAD -----------------------------------------
        UPLOAD_FILE_TYPE_NOT_ALLOWED(10401, "不允许上传的文件类型，仅允许：{}"),
        UPLOAD_FILE_NAME_EXCEED_MAX_LENGTH(10402, "文件名长度超过：{} "),
        UPLOAD_FILE_SIZE_EXCEED_MAX_SIZE(10403, "文件名大小超过：{} MB"),
        UPLOAD_IMPORT_EXCEL_FAILED(10404, "导入excel失败：{}"),
        UPLOAD_FILE_IS_EMPTY(10405, "上传文件为空"),
        UPLOAD_FILE_FAILED(10406, "上传文件失败：{}");

        private final int code;
        private final String msg;

        Business(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        @Override
        public int code() {
            return this.code;
        }

        @Override
        public String message() {
            return this.msg;
        }
    }

    /**
     * 0~99是内部错误码  例如 框架内部问题之类的
     */
    public enum Internal implements ErrorCodeInterface {
        /**
         * 内部错误码
         */
        INVALID_PARAMETER(1, "参数异常：{}"),

        /**
         * 该错误主要用于返回  未知的异常（大部分是RuntimeException） 程序未能捕获 未能预料的错误
         */
        INTERNAL_ERROR(2, "系统内部错误：{}"),

        GET_ENUM_FAILED(3, "获取枚举类型失败, 枚举类：{}"),

        GET_CACHE_FAILED(4, "获取缓存失败：{}"),

        DB_INTERNAL_ERROR(5, "数据库异常"),

        ;

        private final int code;
        private final String msg;

        Internal(int code, String msg) {
            Assert.isTrue(code < 100,
                "错误码code值定义失败，Internal错误码code值范围在0~999之间，请查看ErrorCode.Internal类，当前错误码码为" + name());
            this.code = code;
            this.msg = msg;
        }

        @Override
        public int code() {
            return this.code;
        }

        @Override
        public String message() {
            return this.msg;
        }
    }
}
