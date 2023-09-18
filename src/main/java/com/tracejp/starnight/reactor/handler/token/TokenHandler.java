package com.tracejp.starnight.reactor.handler.token;


import com.tracejp.starnight.reactor.constants.SecurityConstants;
import com.tracejp.starnight.reactor.entity.base.LoginUser;
import com.tracejp.starnight.reactor.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * token验证处理
 *
 * @author yozu
 */
@Component
public class TokenHandler {

    @Autowired
    private RedisUtils redisUtils;

    private final static String ACCESS_TOKEN = "login_tokens:";

    private final static long EXPIRE_TIME = 720;

    private final static int REFRESH_TIME = 120 * 60 * 1000;

    /**
     * 创建令牌
     */
    public Mono<Map<String, Object>> createToken(LoginUser loginUser) {
        String token = IdUtils.fastUUID();
        loginUser.setToken(token);
        refreshToken(loginUser);

        // Jwt存储信息
        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put(SecurityConstants.USER_KEY, token);
        claimsMap.put(SecurityConstants.DETAILS_USER_ID, loginUser.getUserid());
        claimsMap.put(SecurityConstants.DETAILS_USERNAME, loginUser.getUsername());

        // 接口返回信息
        Map<String, Object> rspMap = new HashMap<>();
        rspMap.put("access_token", JwtUtils.createToken(claimsMap));
        rspMap.put("expires_in", EXPIRE_TIME);
        return Mono.just(rspMap);
    }

    /**
     * 获取用户身份信息
     */
    public Mono<LoginUser> getLoginUser(ServerHttpRequest request) {
        // 获取请求携带的令牌
        String token = SecurityUtils.getToken(request);
        return getLoginUser(token);
    }

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public Mono<LoginUser> getLoginUser(String token) {
        try {
            if (StringUtils.isNotEmpty(token)) {
                var userKey = JwtUtils.getUserKey(token);
                return redisUtils.getCacheObject(getTokenKey(userKey))
                        .cast(LoginUser.class);
            }
        } catch (Exception ignored) {
        }
        return Mono.empty();
    }

    /**
     * 设置用户身份信息
     */
    public void setLoginUser(LoginUser loginUser) {
        if (StringUtils.isNotNull(loginUser) && StringUtils.isNotEmpty(loginUser.getToken())) {
            refreshToken(loginUser);
        }
    }

    /**
     * 删除用户缓存信息
     */
    public void delLoginUser(String token) {
        if (StringUtils.isNotEmpty(token)) {
            String userkey = JwtUtils.getUserKey(token);
            redisUtils.deleteObject(getTokenKey(userkey));
        }
    }

    /**
     * 验证令牌有效期，相差不足120分钟，自动刷新缓存
     */
    public void verifyToken(LoginUser loginUser) {
        long expireTime = loginUser.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= REFRESH_TIME) {
            refreshToken(loginUser);
        }
    }

    /**
     * 刷新令牌有效期
     */
    public void refreshToken(LoginUser loginUser) {
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + EXPIRE_TIME * 60 * 1000);
        // 根据uuid将loginUser缓存
        String tokenKey = getTokenKey(loginUser.getToken());
        redisUtils.setCacheObject(tokenKey, loginUser, EXPIRE_TIME, TimeUnit.MINUTES);
    }

    private String getTokenKey(String token) {
        return ACCESS_TOKEN + token;
    }

}
