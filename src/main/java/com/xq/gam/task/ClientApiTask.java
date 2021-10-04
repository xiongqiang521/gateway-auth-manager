package com.xq.gam.task;

import com.xq.gam.convert.Convertor;
import com.xq.gam.entity.client.ClientApiEntity;
import com.xq.gam.mapper.ClientApiMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Description: 路由信息定时查询
 *
 * cache:
 * database:
 *
 * @author 13797
 * @version v0.0.1
 * @date 2021/10/4 10:22
 */
@Service
public class ClientApiTask {
    private static final Logger logger = LoggerFactory.getLogger(ClientApiTask.class);
    private static final Map<String, ClientApiEntity> ROUTE_DEFINITION_MAP = new ConcurrentHashMap<>();

    @Autowired
    private ClientApiMapper clientApiMapper;

    @Autowired
    private RouteDefinitionRepository routeDefinitionRepository;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Scheduled(fixedRate = 60 * 1000)
    public void execute() {
//        extracted();
        applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this));
    }

    private void extracted() {
        logger.debug("start execute gateway route Synchronize. ");
        Map<String, ClientApiEntity> dbMap = clientApiMapper.findAllByEnable(true).stream().collect(Collectors.toMap(Convertor::convertRouteId, Function.identity()));
        ChangeType change = this.getChange(ROUTE_DEFINITION_MAP, dbMap);
        if (!change.isEmpty()) {
            for (ClientApiEntity clientApiEntity : change.getRemove()) {
                String routeDefinitionId = Convertor.convertRouteId(clientApiEntity);
                routeDefinitionRepository.delete(Mono.just(routeDefinitionId)).subscribe();
                ROUTE_DEFINITION_MAP.remove(routeDefinitionId);
                logger.info("execute gateway route Synchronize remove route {}", routeDefinitionId);
            }

            for (ClientApiEntity clientApiEntity : change.getAppend()) {
                String routeDefinitionId = Convertor.convertRouteId(clientApiEntity);
                RouteDefinition routeDefinition = Convertor.convertRouteDefinition(clientApiEntity);
                routeDefinitionRepository.save(Mono.just(routeDefinition)).subscribe();
                ROUTE_DEFINITION_MAP.put(routeDefinitionId, clientApiEntity);
                logger.info("execute gateway route Synchronize append route {}, {}", routeDefinitionId, clientApiEntity);
            }
            applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this));
        }
    }

    private static class ChangeType {
        private final List<ClientApiEntity> append;
        private final List<ClientApiEntity> remove;

        public ChangeType(List<ClientApiEntity> append, List<ClientApiEntity> remove) {

            this.append = append;
            this.remove = remove;
        }

        private boolean isEmpty() {
            return this.append.isEmpty() && this.remove.isEmpty();
        }

        public List<ClientApiEntity> getAppend() {
            return append;
        }

        public List<ClientApiEntity> getRemove() {
            return remove;
        }
    }

    private ChangeType getChange(Map<String, ClientApiEntity> cacheMap, Map<String, ClientApiEntity> dbMap) {
        Map<String, ClientApiEntity> cache = new ConcurrentHashMap<>(cacheMap);
        Map<String, ClientApiEntity> db = new ConcurrentHashMap<>(dbMap);

        List<ClientApiEntity> append = db.keySet().stream()
                .filter(key -> !cache.containsKey(key) || !Objects.equals(db.get(key), cache.get(key)))
                .map(db::get)
                .collect(Collectors.toList());

        List<ClientApiEntity> remove = cache.keySet().stream()
                .filter(key -> !db.containsKey(key) || !Objects.equals(db.get(key), cache.get(key)))
                .map(cache::get)
                .collect(Collectors.toList());

        return new ChangeType(append, remove);
    }

}
