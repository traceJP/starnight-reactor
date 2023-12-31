package com.tracejp.starnight.reactor.constants;

/**
 * Token的Key常量
 *
 * @author yozu
 */
public class TokenConstants {

    /**
     * 令牌自定义标识
     */
    public static final String AUTHENTICATION = "Authorization";

    /**
     * 令牌前缀
     */
    public static final String PREFIX = "Bearer ";

    /**
     * 令牌秘钥
     */
    public final static String SECRET = "abcdefghijklmnopqrstuvwxyz";

    /**
     * Claims中用户信息的key
     */
    public static final String USER_INFO = "userInfo";
}
