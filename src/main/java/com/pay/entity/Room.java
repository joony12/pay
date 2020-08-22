package com.pay.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "room", indexes = {
        @Index(name = "idx_room_room_id", columnList = "room_id")
}, uniqueConstraints = {@UniqueConstraint(columnNames = {"room_id"})})
@EqualsAndHashCode(callSuper = false)
@ToString(exclude = {"user"})
@NoArgsConstructor
public class Room implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_idx", insertable = false, nullable = false)
    private Long idx;

    @Column(name = "room_id")
    private String roomId;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_idx")
    private User user;

    @Builder
    public Room(String roomId, User user) {
        this.roomId = roomId;
        this.user = user;
    }
}