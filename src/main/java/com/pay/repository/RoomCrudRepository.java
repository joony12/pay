package com.pay.repository;

import com.pay.entity.Room;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoomCrudRepository extends CrudRepository<Room, Long> {
    List<Room> findAllByRoomId(String roomId);
}
