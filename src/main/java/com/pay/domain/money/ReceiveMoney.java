package com.pay.domain.money;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "receive_money")
@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ReceiveMoney {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "receive_money_idx", insertable = false, nullable = false)
    private Long idx;

    @Column(name = "receive_money")
    private Long receiveMoney;

    @Column(name = "receive_user_id")
    private String receiveUserId;

    @Column(name = "receive_yn")
    private boolean receiveYn;

    @Column(name = "receive_time")
    private LocalDateTime receiveTime;

    @Builder
    public ReceiveMoney(Long receiveMoney) {
        this.receiveUserId = StringUtils.EMPTY;
        this.receiveMoney = receiveMoney;
        this.receiveYn = false;
        this.receiveTime = null;
    }
}