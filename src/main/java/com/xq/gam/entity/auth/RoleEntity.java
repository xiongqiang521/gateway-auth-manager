package com.xq.gam.entity.auth;

import com.xq.gam.entity.BaseEntity;
import com.xq.gam.entity.user.UserEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

/**
 * 角色 对应实体类
 * @author xiong-qiang
 * @version v1.0
 * 2021/8/29 下午9:36
 */

@Getter
@Setter
@ToString
@Entity
@Table(name = "t_role")
public class RoleEntity extends BaseEntity {
    private static final long serialVersionUID = 2260024591597975578L;

    private String name;
    private String description;
    /**
     * domainId 为空 则系统角色
     */
    private String domainId;

    @ManyToMany(targetEntity = StrategyEntity.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "t_role_strategy",
            //joinColumns,当前对象在中间表中的外键
            joinColumns = {@JoinColumn(name = "role_id")},
            //inverseJoinColumns，对方对象在中间表的外键
            inverseJoinColumns = {@JoinColumn(name = "strategy_id")}
    )
    private List<StrategyEntity> strategyList;

    @ManyToMany(mappedBy = "roleList")
    private List<UserEntity> userList;

}



