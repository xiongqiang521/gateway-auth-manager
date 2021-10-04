package com.xq.gam.entity.client;

import com.xq.gam.constant.OperateType;
import com.xq.gam.entity.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpMethod;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "t_client_api", indexes = @Index(name = "api_index", unique = true, columnList = "method, path"))
public class ClientApiEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 7561124282423170147L;

    /**
     * 求情方法
     */
    @Enumerated(EnumType.STRING)
    private HttpMethod method;

    /**
     * 请求路径
     */
    private String path;

    /**
     * api对应的资源类别
     */
    private String category;

    /**
     * 操作类型
     */
    @Enumerated(EnumType.STRING)
    private OperateType operate;

    /**
     * 是否已经启用路由
     */
    private Boolean enable;

    /**
     * 顺序
     */
    @Column(name = "[order]")
    private Integer order;

    @ManyToOne(targetEntity = ClientEntity.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "client_id")
    @ToString.Exclude
    private ClientEntity client;

}
