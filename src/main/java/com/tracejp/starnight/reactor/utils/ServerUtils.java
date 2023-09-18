package com.tracejp.starnight.reactor.utils;

import com.alibaba.fastjson2.JSON;
import com.tracejp.starnight.reactor.entity.base.R;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.LinkedCaseInsensitiveMap;
import reactor.core.publisher.Mono;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * 客户端工具类
 *
 * @author yozu
 */
public class ServerUtils {
//    /**
//     * 获取String参数
//     */
//    public static String getParameter(String name) {
//        return getRequest().getParameter(name);
//    }
//
//    /**
//     * 获取String参数
//     */
//    public static String getParameter(String name, String defaultValue) {
//        return Convert.toStr(getRequest().getParameter(name), defaultValue);
//    }
//
//    /**
//     * 获取Integer参数
//     */
//    public static Integer getParameterToInt(String name) {
//        return Convert.toInt(getRequest().getParameter(name));
//    }
//
//    /**
//     * 获取Integer参数
//     */
//    public static Integer getParameterToInt(String name, Integer defaultValue) {
//        return Convert.toInt(getRequest().getParameter(name), defaultValue);
//    }
//
//    /**
//     * 获取Boolean参数
//     */
//    public static Boolean getParameterToBool(String name) {
//        return Convert.toBool(getRequest().getParameter(name));
//    }
//
//    /**
//     * 获取Boolean参数
//     */
//    public static Boolean getParameterToBool(String name, Boolean defaultValue) {
//        return Convert.toBool(getRequest().getParameter(name), defaultValue);
//    }
//
//    /**
//     * 获得所有请求参数
//     *
//     * @param request 请求对象{@link ServerHttpRequest}
//     * @return Map
//     */
//    public static Map<String, String[]> getParams(ServerHttpRequest request) {
//        final Map<String, String[]> map = request.getBody();
//        return Collections.unmodifiableMap(map);
//    }
//
//    /**
//     * 获得所有请求参数
//     *
//     * @param request 请求对象{@link ServerHttpRequest}
//     * @return Map
//     */
//    public static Map<String, String> getParamMap(ServerHttpRequest request) {
//        Map<String, String> params = new HashMap<>();
//        for (Map.Entry<String, String[]> entry : getParams(request).entrySet()) {
//            params.put(entry.getKey(), StringUtils.join(entry.getValue(), ","));
//        }
//        return params;
//    }

    /**
     * 获得指定请求头参数
     *
     * @param request 请求对象{@link ServerHttpRequest}
     * @param name    参数名 Key
     * @return String
     */
    public static String getHeader(ServerHttpRequest request, String name) {
        String value = request.getHeaders().getFirst(name);
        if (StringUtils.isEmpty(value)) {
            return StringUtils.EMPTY;
        }
        return urlDecode(value);
    }

    /**
     * 获取请求头参数集合
     *
     * @param request 请求对象{@link ServerHttpRequest}
     * @return Map
     */
    public static Map<String, String> getHeaders(ServerHttpRequest request) {
        Map<String, String> map = new LinkedCaseInsensitiveMap<>();
        HttpHeaders headers = request.getHeaders();
        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            String key = entry.getKey();
            List<String> value = entry.getValue();
            map.put(key, value.get(0));
        }
        return map;
    }

    /**
     * 内容编码
     *
     * @param str 内容
     * @return 编码后的内容
     */
    public static String urlEncode(String str) {
        return URLEncoder.encode(str, StandardCharsets.UTF_8);
    }

    /**
     * 内容解码
     *
     * @param str 内容
     * @return 解码后的内容
     */
    public static String urlDecode(String str) {
        return URLDecoder.decode(str, StandardCharsets.UTF_8);
    }

    /**
     * 设置webflux模型响应
     *
     * @param response ServerHttpResponse
     * @param value    响应内容
     * @return Mono<Void>
     */
    public static Mono<Void> webFluxResponseWriter(ServerHttpResponse response, Object value) {
        return webFluxResponseWriter(response, HttpStatus.OK, value, R.FAIL);
    }

    /**
     * 设置webflux模型响应
     *
     * @param response ServerHttpResponse
     * @param code     响应状态码
     * @param value    响应内容
     * @return Mono<Void>
     */
    public static Mono<Void> webFluxResponseWriter(ServerHttpResponse response, Object value, int code) {
        return webFluxResponseWriter(response, HttpStatus.OK, value, code);
    }

    /**
     * 设置webflux模型响应
     *
     * @param response ServerHttpResponse
     * @param status   http状态码
     * @param code     响应状态码
     * @param value    响应内容
     * @return Mono<Void>
     */
    public static Mono<Void> webFluxResponseWriter(ServerHttpResponse response, HttpStatus status, Object value, int code) {
        return webFluxResponseWriter(response, MediaType.APPLICATION_JSON_VALUE, status, value, code);
    }

    /**
     * 设置webflux模型响应
     *
     * @param response    ServerHttpResponse
     * @param contentType content-type
     * @param status      http状态码
     * @param code        响应状态码
     * @param value       响应内容
     * @return Mono<Void>
     */
    public static Mono<Void> webFluxResponseWriter(ServerHttpResponse response, String contentType, HttpStatus status, Object value, int code) {
        response.setStatusCode(status);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, contentType);
        R<?> result = R.fail(code, value.toString());
        DataBuffer dataBuffer = response.bufferFactory().wrap(JSON.toJSONString(result).getBytes());
        return response.writeWith(Mono.just(dataBuffer));
    }
}
