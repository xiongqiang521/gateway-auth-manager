package com.xq.gam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * describe: 路由的客户端配置
 *
 * @author xiong-qiang
 * @version v1.0
 * 2021/9/13 22:28
 */
@RestController
public class ClientController {

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private RouteDefinitionRepository routeDefinitionRepository;

    @Autowired
    private RouteLocator routeLocator;

    @GetMapping("/route")
    public Flux<Route> updateRoute() {
        return routeLocator.getRoutes();
    }

    @GetMapping("/refresh")
    public Mono<Void> refresh() {
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
        return Mono.empty();
    }

    @GetMapping("/")
    public Flux<RouteDefinition> getRoutes() {
        return routeDefinitionRepository.getRouteDefinitions();
    }

}
