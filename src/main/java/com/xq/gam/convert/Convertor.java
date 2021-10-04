package com.xq.gam.convert;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xq.gam.entity.client.ClientApiEntity;
import com.xq.gam.entity.client.ClientEntity;
import com.xq.gam.entity.route.RouteDefinitionEntity;
import com.xq.gam.util.JackUtil;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Description: 类型转换器
 *
 * @author 13797
 * @version v0.0.1
 * @date 2021/10/4 10:48
 */
public class Convertor {

    private static final TypeReference<List<PredicateDefinition>> PREDICATE_DEFINITION_LIST_TYPE = new TypeReference<List<PredicateDefinition>>() {
    };
    private static final TypeReference<List<FilterDefinition>> FILTER_DEFINITION_LIST_TYPE = new TypeReference<List<FilterDefinition>>() {
    };
    private static final TypeReference<Map<String, Object>> METADATA_MAP_TYPE = new TypeReference<Map<String, Object>>() {
    };

    public static RouteDefinition convertRouteDefinition(RouteDefinitionEntity entity) {
        RouteDefinition routeDefinition = new RouteDefinition();
        routeDefinition.setId(entity.getId());
        routeDefinition.setOrder(entity.getOrder());
        routeDefinition.setUri(URI.create(entity.getUri()));
        routeDefinition.setPredicates(JackUtil.string2Obj(entity.getPredicates(), PREDICATE_DEFINITION_LIST_TYPE));
        routeDefinition.setFilters(JackUtil.string2Obj(entity.getPredicates(), FILTER_DEFINITION_LIST_TYPE));
        routeDefinition.setMetadata(JackUtil.string2Obj(entity.getPredicates(), METADATA_MAP_TYPE));
        return routeDefinition;
    }

    public static RouteDefinitionEntity convertRouteDefinitionEntity(RouteDefinition routeDefinition) {
        RouteDefinitionEntity entity = new RouteDefinitionEntity();
        entity.setId(routeDefinition.getId());
        entity.setOrder(routeDefinition.getOrder());
        entity.setUri(routeDefinition.getUri().toString());
        entity.setPredicates(JackUtil.obj2String(routeDefinition.getPredicates()));
        entity.setPredicates(JackUtil.obj2String(routeDefinition.getPredicates()));
        entity.setMetadata(JackUtil.obj2String(routeDefinition.getMetadata()));
        return entity;
    }

    public static RouteDefinition convertRouteDefinition(ClientApiEntity clientApiEntity) {
        ClientEntity clientEntity = clientApiEntity.getClient();

        List<PredicateDefinition> predicates = new ArrayList<>();
        PredicateDefinition pathDefinition = new PredicateDefinition();
        pathDefinition.setName("Path");
        pathDefinition.setArgs(Collections.singletonMap("Path", clientApiEntity.getPath()));
        predicates.add(pathDefinition);

        PredicateDefinition methodDefinition = new PredicateDefinition();
        methodDefinition.setName("Method");
        methodDefinition.setArgs(Collections.singletonMap("Method", clientApiEntity.getMethod().toString()));
        predicates.add(methodDefinition);

        RouteDefinition routeDefinition = new RouteDefinition();
        routeDefinition.setId(convertRouteId(clientApiEntity));
        routeDefinition.setUri(URI.create(clientEntity.getHost()));
        routeDefinition.setOrder(clientApiEntity.getOrder());
        routeDefinition.setPredicates(predicates);
        return routeDefinition;
    }

    public static String convertRouteId(ClientApiEntity clientApiEntity) {
        return clientApiEntity.getMethod() + ":" +
                clientApiEntity.getPath() + "->" +
                clientApiEntity.getClient().getHost();
    }
}
