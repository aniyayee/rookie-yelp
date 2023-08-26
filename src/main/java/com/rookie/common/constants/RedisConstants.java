package com.rookie.common.constants;

/**
 * @author yayee
 */
public class RedisConstants {

    public static final String LOGIN_CAPTCHA_KEY = "login:captcha:";
    public static final Long LOGIN_CAPTCHA_TTL = 180L;
    public static final String LOGIN_USER_KEY = "login:token:";
    public static final Long LOGIN_USER_TTL = 3600L;

    public static final String CACHE_SHOP_KEY = "cache:shop:";
    public static final Long CACHE_SHOP_TTL = 3600L;
    public static final String CACHE_SHOP_TYPE_KEY = "cache:shopType:all";
    public static final Long CACHE_SHOP_TYPE_TTL = 36000L;
}
