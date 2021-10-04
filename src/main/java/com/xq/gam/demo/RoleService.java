package com.xq.gam.demo;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

/**
 * describe: 自动注册路由配置service
 *
 * @author xiong-qiang
 * @version v1.0
 * 2021/9/13 22:34
 */
//@Configuration
public class RoleService {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        RouteLocatorBuilder.Builder routeBuilder = builder.routes();
        // routeDataSource(routeBuilder);

        return routeBuilder.build();
    }

    private RouteLocatorBuilder.Builder routeDataSource(RouteLocatorBuilder.Builder routeBuilder) {
        routeBuilder
                // .route("common", this::routeReplace)
                .route("baidu", this::baiduReplace)
                .build();
        return routeBuilder;
    }

    private Buildable<Route> baiduReplace(PredicateSpec spec) {
        return spec.path("/s")
                .uri("https://baidu.com");
    }

    private Buildable<Route> routeReplace(PredicateSpec spec) {
        return spec.path("/*")
                .filters(req -> req.rewritePath("(?<pre>.*);(?<ma>.*)", "${pre}%3B${ma}"))
                .uri("http://localhost:80");
    }



}
