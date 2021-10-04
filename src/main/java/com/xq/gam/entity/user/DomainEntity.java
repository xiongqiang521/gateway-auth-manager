package com.xq.gam.entity.user;

import com.xq.gam.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * describe: 租户实体类
 *
 * @author xiong-qiang
 * @version v1.0
 * 2021/8/29 21:42
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "t_domain")
public class DomainEntity extends BaseEntity {

    private static final long serialVersionUID = -6295020985678603019L;

    @Column(unique = true)
    private String domainId;

    private String domainName;

    private String description;

    /**
     * 一个租户对应一个同名的user
     */
    @OneToOne(targetEntity = UserEntity.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", unique = true)
    @ToString.Exclude
    private UserEntity userEntity;

}
