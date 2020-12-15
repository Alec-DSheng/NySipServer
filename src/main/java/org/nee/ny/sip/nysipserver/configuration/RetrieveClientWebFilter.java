package org.nee.ny.sip.nysipserver.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;
import java.util.Objects;

/**
 * @Author: alec
 * Description:
 * @date: 14:41 2020-12-14
 */
@Component
@Slf4j
public class RetrieveClientWebFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        InetSocketAddress inetSocketAddress = serverWebExchange.getRequest().getRemoteAddress();
        String address = Objects.requireNonNull(inetSocketAddress).getAddress().getHostAddress();
        log.info("请求IP地址 {}", address);
        return webFilterChain.filter(serverWebExchange);
    }
}
