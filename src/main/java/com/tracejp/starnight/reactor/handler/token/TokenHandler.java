package com.tracejp.starnight.reactor.handler.token;


import com.tracejp.starnight.reactor.constants.SecurityConstants;
import com.tracejp.starnight.reactor.entity.base.LoginUser;
import com.tracejp.starnight.reactor.utils.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * token验证处理
 *
 * @author yozu
 */
@RequiredArgsConstructor
@Component
public class TokenHandler {

    private final RedisUtils redisUtils;

    private final static String ACCESS_TOKEN = "login_tokens:";

    private final static long EXPIRE_TIME = 720;

    private final static int REFRESH_TIME = 120 * 60 * 1000;

    /**
     * 创建令牌
     */
    public Mono<Map<String, Object>> createToken(LoginUser loginUser) {
        String token = IdUtils.fastUUID();
        loginUser.setToken(token);
        Mono<Boolean> refresh = refreshToken(loginUser);

        // Jwt存储信息
        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put(SecurityConstants.USER_KEY, token);
        claimsMap.put(SecurityConstants.DETAILS_USER_ID, loginUser.getUserid());
        claimsMap.put(SecurityConstants.DETAILS_USERNAME, loginUser.getUsername());

        // 接口返回信息
        Map<String, Object> rspMap = new HashMap<>();
        rspMap.put("access_token", JwtUtils.createToken(claimsMap));
        rspMap.put("expires_in", EXPIRE_TIME);
        return Mono.zip(refresh, Mono.just(rspMap))
                .map(Tuple2::getT2);
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
        if (StringUtils.isNotEmpty(token)) {
            var userKey = JwtUtils.getUserKey(token);
            return redisUtils.getCacheObject(getTokenKey(userKey))
                    .cast(LoginUser.class);
        }
        return Mono.empty();
    }

    /**
     * 设置用户身份信息
     */
    public Mono<Boolean> setLoginUser(LoginUser loginUser) {
        if (StringUtils.isNotNull(loginUser) && StringUtils.isNotEmpty(loginUser.getToken())) {
            return refreshToken(loginUser);
        }
        return Mono.just(false);
    }

    /**
     * 删除用户缓存信息
     */
    public Mono<Long> delLoginUser(String token) {
        if (StringUtils.isNotEmpty(token)) {
            String userKey = JwtUtils.getUserKey(token);
            return redisUtils.deleteObject(getTokenKey(userKey));
        }
        return Mono.just(0L);
    }

    /**
     * 验证令牌有效期，相差不足120分钟，自动刷新缓存
     */
    public Mono<Boolean> verifyToken(LoginUser loginUser) {
        long expireTime = loginUser.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= REFRESH_TIME) {
            return refreshToken(loginUser);
        }
        return Mono.just(false);
    }

    /**
     * 刷新令牌有效期
     */
    public Mono<Boolean> refreshToken(LoginUser loginUser) {
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + EXPIRE_TIME * 60 * 1000);
        // 根据uuid将loginUser缓存
        String tokenKey = getTokenKey(loginUser.getToken());
        return redisUtils.setCacheObject(tokenKey, loginUser, EXPIRE_TIME, TimeUnit.MINUTES);
    }

    private String getTokenKey(String token) {
        return ACCESS_TOKEN + token;
    }

}
