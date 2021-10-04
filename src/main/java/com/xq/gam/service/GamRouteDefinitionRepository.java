package com.xq.gam.service;

import com.xq.gam.convert.Convertor;
import com.xq.gam.mapper.RouteDefinitionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Stream;

/**
 * describe: 自定义实现 RouteDefinition 管理，可存数据库
 *
 * @author xiong-qiang
 * @version v1.0
 * 2021/9/14 22:46
 */
@Service
public class GamRouteDefinitionRepository implements RouteDefinitionRepository {

    private static final Logger logger = LoggerFactory.getLogger(GamRouteDefinitionRepository.class);

    private final RouteDefinitionMapper routeDefinitionMapper;

    public GamRouteDefinitionRepository(RouteDefinitionMapper routeDefinitionMapper) {
        this.routeDefinitionMapper = routeDefinitionMapper;
    }

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        logger.debug("Access getRouteDefinitions method. ");
        Stream<RouteDefinition> routeDefinitionStream = routeDefinitionMapper.findAll()
                .stream().map(Convertor::convertRouteDefinition);
        return Flux.fromStream(routeDefinitionStream);
    }

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        logger.info("save RouteDefinition. RouteDefinition {}", route);
        return route.map(Convertor::convertRouteDefinitionEntity)
                .map(r -> routeDefinitionMapper.save(r))
                .thenEmpty(Mono.empty());
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        logger.info("delete RouteDefinition. routeId {}", routeId);
        return routeId.doOnNext(rId -> routeDefinitionMapper.deleteById(rId))
                .thenEmpty(Mono.empty());
    }
}
