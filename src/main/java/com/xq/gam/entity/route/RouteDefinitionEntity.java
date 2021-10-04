package com.xq.gam.entity.route;

import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.gateway.route.RouteDefinition;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Description: spring gateway 中 RouteDefinition 对应实体类
 *
 * @see RouteDefinition
 * @author 13797
 * @version v0.0.1
 * @date 2021/10/4 19:35
 */
@Getter
@Setter
@Entity
@Table(name = "t_route_definition")
public class RouteDefinitionEntity {

    @Id
    private String id;

    private String uri;

    @Column(name = "predicates", columnDefinition = "json")
    private String predicates;

    @Column(name = "filters", columnDefinition = "json")
    private String filters;

    @Column(name = "metadata", columnDefinition = "json")
    private String metadata;

    @Column(name = "[order]")
    private int order = 0;
}
