package com.xq.gam.controller;

import com.xq.gam.convert.Convertor;
import com.xq.gam.entity.client.ClientApiEntity;
import com.xq.gam.exception.ExceptionEnum;
import com.xq.gam.exception.ServiceException;
import com.xq.gam.mapper.ClientApiMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

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

    @Autowired
    private ClientApiMapper clientApiMapper;

    @GetMapping("/client_apis")
    public Flux<ClientApiEntity> getList() {
        List<ClientApiEntity> all = clientApiMapper.findAll();
        System.out.println(all);
        return Flux.fromIterable(all);

    }

    @PutMapping("/client_apis/{api_id}/open")
    public Mono<RouteDefinition> openApiRoute(@PathVariable(name = "api_id") Long apiId) {
        ClientApiEntity clientApiEntity = clientApiMapper.findById(apiId)
                .orElseThrow(() -> new ServiceException(ExceptionEnum.RESOURCE_NOT_FIND));
        clientApiEntity.setEnable(true);
        clientApiMapper.saveAndFlush(clientApiEntity);
        RouteDefinition routeDefinition = Convertor.convertRouteDefinition(clientApiEntity);

        routeDefinitionRepository.save(Mono.just(routeDefinition));
        // 两个 Mono.just(routeDefinition) 不是同一个对象，save时会将Mono关闭
        return Mono.just(routeDefinition);
    }

    @PutMapping("/client_apis/{api_id}/close")
    public Mono<RouteDefinition> closeApiRoute(@PathVariable(name = "api_id") Long apiId) {
        ClientApiEntity clientApiEntity = clientApiMapper.getById(apiId);
        clientApiEntity.setEnable(false);
        clientApiMapper.saveAndFlush(clientApiEntity);
        RouteDefinition routeDefinition = Convertor.convertRouteDefinition(clientApiEntity);

        routeDefinitionRepository.delete(Mono.just(routeDefinition.getId()));
        return Mono.just(routeDefinition);
    }

}
