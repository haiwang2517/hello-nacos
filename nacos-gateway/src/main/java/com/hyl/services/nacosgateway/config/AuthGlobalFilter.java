package com.hyl.services.nacosgateway.config;

import com.alibaba.nacos.common.utils.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 如果要对请求进行拦截直接return null；即可，放行直接return chain.filter(exchange)即可，非常的方便
        String path = exchange.getRequest().getURI().getPath();
        // TODO 白名单，不需要拦截的URI path

        String token = exchange.getRequest().getHeaders().getFirst("JWT_TOKEN_HEADER");
        if(StringUtils.isBlank(token)){
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return error(exchange, "未认证");
        }

        // TODO 认证超时提醒
        if("123".equals(token)){
            return error(exchange, "认证超时");
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }

    private Mono<Void> error(ServerWebExchange exchange, String message){
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        exchange.getResponse().getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        DataBuffer dataBuffer = exchange.getResponse().bufferFactory().wrap(String.format("{ \"%s\": \"%s\", \"%s\": \"%s\"}", "code", HttpStatus.UNAUTHORIZED.value(), "msg", message).getBytes());
        return exchange.getResponse().writeWith(Flux.just(dataBuffer));
    }
}
