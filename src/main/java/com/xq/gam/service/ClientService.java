package com.xq.gam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.ArrayList;

/**
 * describe: 客户端配置service
 *
 * @author xiong-qiang
 * @version v1.0
 * 2021/9/13 22:33
 */
@Service
public class ClientService {
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private RouteDefinitionRepository routeDefinitionRepository;

    public void addDefaultRoute() {
        RouteDefinition definition = new RouteDefinition();
        definition.setId("123456");
        ArrayList<PredicateDefinition> predicates = new ArrayList<>();
        PredicateDefinition e = new PredicateDefinition();
        predicates.add(e);
        definition.setPredicates(predicates);
        definition.setUri(URI.create("https://www.baidu.com"));

        addRoute(definition);
        publish();
    }

    private void addRoute(RouteDefinition definition) {
        try {
            routeDefinitionRepository.save(Mono.just(definition)).subscribe();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void publish() {
        this.applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this.routeDefinitionRepository));
    }


}
