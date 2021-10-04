package com.xq.gam.mapper;

import com.xq.gam.entity.auth.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Description: RoleEntity 对应 mapper
 *
 * @author 13797
 * @version v0.0.1
 * @date 2021/10/4 10:39
 */
public interface RoleMapper extends JpaRepository<RoleEntity, Long> {
}
