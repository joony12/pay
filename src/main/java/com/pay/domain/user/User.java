package com.pay.domain.user;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;

@Getter
@Setter
@Entity
@EqualsAndHashCode(callSuper = false)
@Table(name = "user", indexes = {
        @Index(name = "idx_user_user_id", columnList = "user_id")
}, uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id"})
})
@NoArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_idx", insertable = false, nullable = false)
    private Long userIdx;

    @Column(name = "user_id")
    private String userId;

    @Builder
    public User(String userId) {
        this.userId = userId;
    }
}
