package com.tracejp.starnight.reactor.frame;

import com.alibaba.fastjson2.support.spring6.data.redis.GenericFastJsonRedisSerializer;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis配置
 *
 * @author yozu
 */
@Configuration
@EnableCaching
@AutoConfigureBefore(RedisReactiveAutoConfiguration.class)
public class RedisConfig {

    /**
     * 配置自定义redisTemplate
     * @see org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration
     * 自动装配了一个默认的 ReactiveStringRedisTemplate 导致重复装配
     */
    @Bean
    @Primary
    public ReactiveRedisTemplate<String, Object> reactiveRedisTemplate(ReactiveRedisConnectionFactory connectionFactory) {
        var stringSerializer = new StringRedisSerializer();
        var objectSerializer = new GenericFastJsonRedisSerializer();
        RedisSerializationContext<String, Object> serializer = RedisSerializationContext.<String, Object>newSerializationContext()
                .key(stringSerializer)
                .value(objectSerializer)
                .hashKey(stringSerializer)
                .hashValue(objectSerializer)
                .build();
        return new ReactiveRedisTemplate<>(connectionFactory, serializer);
    }

}
