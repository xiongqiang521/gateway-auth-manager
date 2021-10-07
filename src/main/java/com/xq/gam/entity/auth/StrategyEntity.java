package com.xq.gam.entity.auth;

import com.xq.gam.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

/**
 * describe: 策略表对应实体类
 *
 * @author xiong-qiang
 * @version v1.0
 * 2021/9/11 20:04
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "t_strategy")
public class StrategyEntity extends BaseEntity {
    private static final long serialVersionUID = 2812387869245236385L;

    private String name;
    private String domainId;

    @ManyToMany(mappedBy = "strategyList")
    private List<RoleEntity> roleList;

    @OneToMany(mappedBy = "strategy")
    private List<ResourceAreaEntity> resourceAreaList;


}
