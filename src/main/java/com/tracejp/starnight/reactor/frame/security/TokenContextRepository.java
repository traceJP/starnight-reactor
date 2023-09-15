package com.tracejp.starnight.reactor.frame.security;

import com.tracejp.starnight.reactor.entity.UserEntity;
import com.tracejp.starnight.reactor.entity.base.LoginUser;
import com.tracejp.starnight.reactor.entity.enums.RoleEnum;
import com.tracejp.starnight.reactor.exception.ServiceException;
import com.tracejp.starnight.reactor.handler.token.TokenHandler;
import com.tracejp.starnight.reactor.utils.SecurityUtils;
import com.tracejp.starnight.reactor.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/9/15 14:50
 */
@RequiredArgsConstructor
@Component
public class TokenContextRepository implements ServerSecurityContextRepository {

    private final TokenHandler tokenHandler;

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return Mono.empty();
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        String token = SecurityUtils.getToken(request);

        // 匿名请求
        if (StringUtils.isEmpty(token)) {
            return Mono.empty();
        }

        // token 验证
        LoginUser loginUser = tokenHandler.getLoginUser(token);
        if (loginUser != null) {
            // 存放 token 信息到 security
            UsernamePasswordAuthenticationToken authToken = buildToken(loginUser);
            SecurityContextHolder.getContext().setAuthentication(authToken);
            return Mono.just(SecurityContextHolder.getContext());
        }

        return Mono.error(new ServiceException("登录过期"));
    }

    /**
     * 构建 security 用户实体
     */
    private UsernamePasswordAuthenticationToken buildToken(LoginUser loginUser) {
        UserEntity user = loginUser.getUser();
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(RoleEnum.fromCode(user.getRole()).getRoleName()));
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                user.getUserName(), user.getPassword(), grantedAuthorities
        );
        authToken.setDetails(loginUser);
        return authToken;
    }

}
