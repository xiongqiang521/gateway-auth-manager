package com.xq.gam.mapper;

import com.xq.gam.entity.client.ClientApiEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Description: ClientApiEntity 对应 mapper
 *
 * @author 13797
 * @version v0.0.1
 * @date 2021/10/4 10:39
 */
public interface ClientApiMapper extends JpaRepository<ClientApiEntity, Long> {

    public List<ClientApiEntity> findAllByEnable(boolean enable);
}
