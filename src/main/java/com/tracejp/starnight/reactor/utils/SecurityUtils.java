package com.tracejp.starnight.reactor.utils;


import com.tracejp.starnight.reactor.constants.TokenConstants;
import com.tracejp.starnight.reactor.entity.base.LoginUser;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import reactor.core.publisher.Mono;

/**
 * 权限获取工具类
 *
 * @author yozu
 */
public class SecurityUtils {

    /**
     * 获取用户ID
     */
    public static Mono<Long> getUserId() {
        return getLoginUser().map(LoginUser::getUserid);
    }

    /**
     * 获取用户名称
     */
    public static Mono<String> getUsername() {
        return getLoginUser().map(LoginUser::getUsername);
    }

    /**
     * 获取用户key
     */
    public static Mono<String> getUserToken() {
        return getLoginUser().map(LoginUser::getToken);
    }

    /**
     * 获取登录用户信息
     */
    public static Mono<LoginUser> getLoginUser() {
        return ReactiveSecurityContextHolder.getContext()
                .map(context -> context.getAuthentication().getDetails())
                .cast(LoginUser.class);
    }

    /**
     * 根据 request 获取请求token
     */
    public static String getToken(ServerHttpRequest request) {
        // 从header获取token标识
        String token = ServerUtils.getHeader(request, TokenConstants.AUTHENTICATION);
        return replaceTokenPrefix(token);
    }

    /**
     * 裁剪token前缀
     */
    public static String replaceTokenPrefix(String token) {
        // 如果前端设置了令牌前缀，则裁剪掉前缀
        if (StringUtils.isNotEmpty(token) && token.startsWith(TokenConstants.PREFIX)) {
            token = token.replaceFirst(TokenConstants.PREFIX, "");
        }
        return token;
    }

    /**
     * 生成BCryptPasswordEncoder密码
     *
     * @param password 密码
     * @return 加密字符串
     */
    public static String encryptPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    /**
     * 判断密码是否相同
     *
     * @param rawPassword     真实密码
     * @param encodedPassword 加密后字符
     * @return 结果
     */
    public static boolean matchesPassword(String rawPassword, String encodedPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

}
