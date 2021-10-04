package com.xq.gam.entity.auth;

import com.xq.gam.constant.OperateType;
import com.xq.gam.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * describe: 资源区表对应实体类
 *
 * @author xiong-qiang
 * @version v1.0
 * 2021/9/11 20:36
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "t_resource_area")
public class ResourceAreaEntity extends BaseEntity {
    private static final long serialVersionUID = 5934883083140628726L;

    @Enumerated(EnumType.STRING)
    private StrategyType strategyType;

    @Enumerated(EnumType.STRING)
    // @Convert(converter = OperateTypeConverter.class)
    private OperateType operateType;

    /**
     * 如果 OperateType 为 SINGLE_API， resource存放 t_client_api 表中的 path 字段
     * 如果 OperateType 为 CATEGORY_API， resource存放 t_client_api 表中的 category 字段
     */
    private String resource;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "strategy_id")
    private StrategyEntity strategy;


    public static enum  StrategyType {
        CATEGORY_API,
        SINGLE_API,
        ;
    }
}
