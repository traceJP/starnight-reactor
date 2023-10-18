package com.tracejp.starnight.reactor.frame;


import com.tracejp.starnight.reactor.entity.enums.RoleEnum;
import com.tracejp.starnight.reactor.frame.properties.SecurityConfigProperties;
import com.tracejp.starnight.reactor.frame.security.RestAccessDeniedHandler;
import com.tracejp.starnight.reactor.frame.security.RestAuthEntryPoint;
import com.tracejp.starnight.reactor.frame.security.TokenContextRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.util.List;

/**
 * <p> Spring Security 配置 <p/>
 *
 * @author traceJP
 * @since 2023/5/23 9:08
 */
@RequiredArgsConstructor
@Configuration
@EnableWebFluxSecurity
@EnableConfigurationProperties(SecurityConfigProperties.class)
public class SecurityConfig {

    private final SecurityConfigProperties configProperties;

    private final TokenContextRepository tokenContextRepository;

    private final RestAccessDeniedHandler restAccessDeniedHandler;

    private final RestAuthEntryPoint restAuthEntryPoint;

    /**
     * Security 配置
     */
    @Bean
    public SecurityWebFilterChain configure(ServerHttpSecurity httpSecurity) {

        // 白名单接口
        List<String> securityIgnoreUrls = configProperties.getIgnores();

        return httpSecurity
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .logout(ServerHttpSecurity.LogoutSpec::disable)
                .headers(item -> item.frameOptions(ServerHttpSecurity.HeaderSpec.FrameOptionsSpec::disable))
                .securityContextRepository(tokenContextRepository)
                .authorizeExchange(auth -> auth.anyExchange().permitAll())  // 测试放行
//                .authorizeExchange(auth -> auth
//                        // 白名单接口放行
//                        .pathMatchers(securityIgnoreUrls.toArray(new String[0])).permitAll()
//                        // admin 接口鉴权
//                        .pathMatchers("/api/admin/**").hasRole(RoleEnum.ADMIN.getName())
//                        // student 接口鉴权
//                        .pathMatchers("/api/student/**").hasRole(RoleEnum.STUDENT.getName())
//                        // global 接口认证
//                        .anyExchange().authenticated()
//                )
                .exceptionHandling(auth -> auth
                        // 未登录异常处理
                        .authenticationEntryPoint(restAuthEntryPoint)
                        // 无权限异常处理
                        .accessDeniedHandler(restAccessDeniedHandler)
                )
                .build();
    }

}
