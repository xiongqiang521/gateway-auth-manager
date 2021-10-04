package com.xq.gam.mapper;

import com.xq.gam.entity.user.DomainEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Description: DomainEntity 对应 mapper
 *
 * @author 13797
 * @version v0.0.1
 * @date 2021/10/4 10:39
 */
public interface DomainMapper extends JpaRepository<DomainEntity, Long> {
}
