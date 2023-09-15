package com.tracejp.starnight.reactor.entity.param;

import com.tracejp.starnight.reactor.entity.enums.RoleEnum;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/9/15 10:20
 */
public record LoginParam(String username, String password, RoleEnum role) {

    /**
     * 获取 security 包装对象
     *
     * @return UsernamePasswordAuthenticationToken
     */
    public UsernamePasswordAuthenticationToken getAuthenticationToken() {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        token.setDetails(role);
        return token;
    }

}
