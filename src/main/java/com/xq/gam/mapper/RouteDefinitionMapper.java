package com.xq.gam.mapper;

import com.xq.gam.entity.route.RouteDefinitionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteDefinitionMapper extends JpaRepository<RouteDefinitionEntity, String> {
}