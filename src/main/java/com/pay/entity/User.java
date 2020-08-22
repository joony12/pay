package com.pay.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@EqualsAndHashCode(callSuper = false)
@Table(name = "user", indexes = {
        @Index(name = "idx01_user_id", columnList = "user_id")
}, uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id"})
})
@NoArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_idx", insertable = false, nullable = false)
    private Long idx;

    @Column(name = "user_id")
    private Long userId;

    @Builder
    public User(Long userId) {
        this.userId = userId;
    }
}
