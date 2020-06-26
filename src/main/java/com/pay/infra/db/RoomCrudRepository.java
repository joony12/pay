package com.pay.infra.db;

import com.pay.domain.room.Room;
import com.pay.domain.user.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoomCrudRepository extends CrudRepository<Room, Long> {

    List<Room> findRoomByRoomId(String roomId);

    Boolean existsByRoomIdAndUser(String roomId, User user);
}
