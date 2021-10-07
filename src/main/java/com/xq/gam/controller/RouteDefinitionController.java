package com.xq.gam.controller;

import com.xq.gam.mapper.ClientApiMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * Description:
 *
 * @author 13797
 * @version v0.0.1
 * 2021/10/6 22:40
 */
@RestController
public class RouteDefinitionController {

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private RouteDefinitionRepository routeDefinitionRepository;

    @Autowired
    private ClientApiMapper clientApiMapper;

    @GetMapping("/route_definitions")
    public Flux<RouteDefinition> getRouteDefinitionList() {
        return routeDefinitionRepository.getRouteDefinitions();
    }

}
