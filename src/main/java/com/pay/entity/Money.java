package com.pay.entity;

import com.pay.util.type.MoneyTypeEnum;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "money", indexes = {
        @Index(name = "idx_spread_token", columnList = "token")
})
@EqualsAndHashCode(callSuper = false)
@ToString(exclude = {"room"})
@NoArgsConstructor
public class Money implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "money_idx", insertable = false, nullable = false)
    private Long idx;

    @ManyToOne(targetEntity = Room.class)
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(name = "token")
    private String token;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private MoneyTypeEnum moneyTypeEnum;

    @Column(name = "money")
    private int money;

    @Column(name = "user_count")
    private int userCount;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "create_dt")
    private LocalDateTime createDt;

    @Column(name = "update_dt")
    private LocalDateTime updateDt;

    @Version
    private int version;

    @Builder
    public Money(Room room, String token, int userCount, MoneyTypeEnum moneyTypeEnum, int money, Long userId, LocalDateTime createDt, LocalDateTime updateDt) {
        this.room = room;
        this.token = token;
        this.userCount = userCount;
        this.moneyTypeEnum = moneyTypeEnum;
        this.money = money;
        this.userId = userId;
        this.createDt = createDt;
        this.updateDt = updateDt;
    }
}