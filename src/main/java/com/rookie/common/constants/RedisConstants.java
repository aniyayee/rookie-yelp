package com.rookie.common.constants;

/**
 * @author yayee
 */
public class RedisConstants {

    public static final String LOGIN_CAPTCHA_KEY = "login:captcha:";
    public static final Long LOGIN_CAPTCHA_TTL = 180L;
    public static final String LOGIN_USER_KEY = "login:token:";
    public static final Long LOGIN_USER_TTL = 3600L;
}
