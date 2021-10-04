package com.xq.gam.entity.user;

import com.xq.gam.entity.BaseEntity;
import com.xq.gam.entity.auth.RoleEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * describe: user实体类
 *
 * @author xiong-qiang
 * @version v1.0
 * 2021/8/29 21:41
 */
@Getter
@Setter
@ToString
@Table(name = "t_user",
        indexes = @Index(columnList = "user_id", unique = true),
        uniqueConstraints = @UniqueConstraint(name = "unique_username", columnNames = {"user_name", "domain_id"}))

@Entity
public class UserEntity extends BaseEntity {

    private static final long serialVersionUID = -4374467788928061117L;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_name")
    private String userName;

    @ToString.Exclude
    private String password;

    /**
     * domain 区分不同租户
     */
    @Column(name = "domain_id")
    private String domainId;
    private String phone;
    private String email;
    private String description;

    /**
     * 冻结截止时间
     */
    private LocalDateTime freezeTime;

    @ManyToMany(targetEntity = RoleEntity.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "t_user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private List<RoleEntity> roleList;

}
