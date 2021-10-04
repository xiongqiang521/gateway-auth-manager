package com.xq.gam.entity.client;

import com.xq.gam.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@Entity
@Table(name = "t_client", indexes = @Index(name = "name_unique", unique = true, columnList = "name"))
public class ClientEntity extends BaseEntity {
    private static final long serialVersionUID = -7948699281517015117L;

    /**
     * 客户端地址，如：http://localhost:8080
     */
    private String host;

    /**
     * 客户端名称
     */
    private String name;

    /**
     * 微服务注册路由时客户端id
     */
    private String clientId;

    /**
     * 微服务注册路由时客户端秘钥
     */
    @ToString.Exclude
    private String clientSecret;

    @OneToMany(targetEntity = ClientApiEntity.class, mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<ClientApiEntity> apiList;

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public boolean equals(ClientEntity other) {
        return this.getClientId().equals(other.getClientId()) &&
                this.getHost().equals(other.getHost()) &&
                this.getName().equals(other.getName());

    }
}
