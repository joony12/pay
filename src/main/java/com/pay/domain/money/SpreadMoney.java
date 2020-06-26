package com.pay.domain.money;

import com.pay.domain.room.Room;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "spread_money")
@EqualsAndHashCode(callSuper = false)
@ToString(exclude = {"room", "receiveMonies"})
@NoArgsConstructor
public class SpreadMoney {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "spread_money_idx", insertable = false, nullable = false)
    private Long idx;

    @ManyToOne(targetEntity = Room.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "room_idx")
    private Room room;

    @Column
    private String token;

    @Column(name = "spread_amount_money")
    private Long spreadAmountMoney;

    @Column(name = "spread_user_count")
    private Long spreadUserCount;

    @Column(name = "spread_start_time")
    private LocalDateTime spreadStartTime;

    @Column(name = "spread_user_id")
    private String spreadUserId;

    @OneToMany
    @JoinColumn(name="spread_money_idx")
    private List<ReceiveMoney> receiveMonies;

    @Builder
    public SpreadMoney(Room room, String token, Long spreadUserCount, Long spreadAmountMoney, String spreadUserId, List<ReceiveMoney> receiveMonies) {
        this.room = room;
        this.token = token;
        this.spreadUserId = spreadUserId;
        this.spreadAmountMoney = spreadAmountMoney;
        this.spreadUserCount = spreadUserCount;
        this.spreadStartTime = LocalDateTime.now();
        this.receiveMonies = receiveMonies;
    }
}