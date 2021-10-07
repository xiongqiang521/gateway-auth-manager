package com.xq.gam.convert;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xq.gam.entity.client.ClientApiEntity;
import com.xq.gam.entity.client.ClientEntity;
import com.xq.gam.entity.route.RouteDefinitionEntity;
import com.xq.gam.filter.factory.AuthGatewayFilterFactory;
import com.xq.gam.util.JackUtil;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;

import java.net.URI;
import java.util.*;

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
        routeDefinition.setFilters(JackUtil.string2Obj(entity.getFilters(), FILTER_DEFINITION_LIST_TYPE));
        routeDefinition.setMetadata(JackUtil.string2Obj(entity.getMetadata(), METADATA_MAP_TYPE));
        return routeDefinition;
    }

    public static RouteDefinitionEntity convertRouteDefinitionEntity(RouteDefinition routeDefinition) {
        RouteDefinitionEntity entity = new RouteDefinitionEntity();
        entity.setId(routeDefinition.getId());
        entity.setOrder(routeDefinition.getOrder());
        entity.setUri(routeDefinition.getUri().toString());
        entity.setPredicates(JackUtil.obj2String(routeDefinition.getPredicates()));
        entity.setFilters(JackUtil.obj2String(routeDefinition.getFilters()));
        entity.setMetadata(JackUtil.obj2String(routeDefinition.getMetadata()));
        return entity;
    }

    public static RouteDefinition convertRouteDefinition(ClientApiEntity clientApiEntity) {
        ClientEntity clientEntity = clientApiEntity.getClient();

        // 路径执行器，path
        List<PredicateDefinition> predicates = new ArrayList<>();
        PredicateDefinition pathDefinition = new PredicateDefinition();
        pathDefinition.setName("Path");
        pathDefinition.setArgs(Collections.singletonMap("Path", clientApiEntity.getPath()));
        predicates.add(pathDefinition);

        // 方法执行器，POST、GET等方法
        PredicateDefinition methodDefinition = new PredicateDefinition();
        methodDefinition.setName("Method");
        methodDefinition.setArgs(Collections.singletonMap("Method", clientApiEntity.getMethod().toString()));
        predicates.add(methodDefinition);

        // 权限过滤器
        FilterDefinition authFilterDefinition = new FilterDefinition();
        authFilterDefinition.setName("Auth");
        HashMap<String, String> authFilterParam = new HashMap<>();
        authFilterParam.put(AuthGatewayFilterFactory.OPERATE_FILED, clientApiEntity.getOperate().toString());
        authFilterParam.put(AuthGatewayFilterFactory.CATEGORY_FILED, clientApiEntity.getCategory());
        authFilterParam.put(AuthGatewayFilterFactory.PATH_FILED, clientApiEntity.getPath());
        authFilterDefinition.setArgs(authFilterParam);

        // 封装 RouteDefinition
        RouteDefinition routeDefinition = new RouteDefinition();
        routeDefinition.setId(convertRouteId(clientApiEntity));
        routeDefinition.setUri(URI.create(clientEntity.getHost()));
        routeDefinition.setOrder(clientApiEntity.getOrder());
        routeDefinition.setPredicates(predicates);
        routeDefinition.setFilters(Collections.singletonList(authFilterDefinition));
        return routeDefinition;
    }

    public static String convertRouteId(ClientApiEntity clientApiEntity) {
        return clientApiEntity.getMethod() + ":" +
                clientApiEntity.getPath() + "->" +
                clientApiEntity.getClient().getHost();
    }
}
