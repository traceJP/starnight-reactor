package com.tracejp.starnight.reactor.frame.security;

import com.tracejp.starnight.reactor.entity.base.LoginUser;
import com.tracejp.starnight.reactor.entity.enums.RoleEnum;
import com.tracejp.starnight.reactor.handler.token.TokenHandler;
import com.tracejp.starnight.reactor.utils.SecurityUtils;
import com.tracejp.starnight.reactor.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
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
        System.out.println("这里 执行了！！！");
        return Mono.empty();
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {

//        System.out.println("这里执行力！！！");

        var request = exchange.getRequest();
        var token = SecurityUtils.getToken(request);

        // 匿名请求
        if (StringUtils.isEmpty(token)) {
            return Mono.empty();
        }

        // token 验证
        return tokenHandler.getLoginUser(token).flatMap(loginUser -> {
            var authToken = buildToken(loginUser);
            ReactiveSecurityContextHolder.withAuthentication(authToken);
            return Mono.just(new SecurityContextImpl(buildToken(loginUser)));
        });
    }

    /**
     * 构建 security 用户实体
     */
    private UsernamePasswordAuthenticationToken buildToken(LoginUser loginUser) {
        var user = loginUser.getUser();
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(RoleEnum.fromCode(user.getRole()).getRoleName()));
        var authToken = new UsernamePasswordAuthenticationToken(
                user.getUserName(), user.getPassword(), grantedAuthorities
        );
        authToken.setDetails(loginUser);
        return authToken;
    }

}
