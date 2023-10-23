package com.tracejp.starnight.reactor.frame.security;

import com.tracejp.starnight.reactor.entity.UserEntity;
import com.tracejp.starnight.reactor.entity.base.LoginUser;
import com.tracejp.starnight.reactor.entity.enums.RoleEnum;
import com.tracejp.starnight.reactor.entity.enums.UserStatusEnum;
import com.tracejp.starnight.reactor.service.IUserService;
import com.tracejp.starnight.reactor.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p> 认证 <p/>
 *
 * @author traceJP
 * @since 2023/9/15 10:09
 */
@RequiredArgsConstructor
@Component
public class LoginAuthProvider implements ReactiveAuthenticationManager {

    private final IUserService userService;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        RoleEnum role = (RoleEnum) authentication.getDetails();

        // 登录检查
        return userService.getByUserName(username).flatMap(user -> {

            if (user == null) {
                return Mono.error(new BadCredentialsException("用户名或密码错误"));
            }

            if (user.getRole() != role.getCode()) {
                return Mono.error(new BadCredentialsException("用户名或密码错误"));
            }

            boolean result = SecurityUtils.matchesPassword(password, user.getPassword());
            if (!result) {
                return Mono.error(new BadCredentialsException("用户名或密码错误"));
            }

            UserStatusEnum userStatusEnum = UserStatusEnum.fromCode(user.getStatus());
            if (UserStatusEnum.Disable == userStatusEnum) {
                return Mono.error(new BadCredentialsException("用户被禁用"));
            }

            // 登录成功
            user.setLastActiveTime(LocalDateTime.now());
            return userService.updateById(user).flatMap(this::buildToken);
        });
    }

    /**
     * 构建 security 用户实体
     */
    private Mono<UsernamePasswordAuthenticationToken> buildToken(UserEntity user) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(RoleEnum.fromCode(user.getRole()).getRoleName()));
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                user.getUserName(), user.getPassword(), grantedAuthorities
        );
        authToken.setDetails(LoginUser.build(user));
        return Mono.just(authToken);
    }

}
