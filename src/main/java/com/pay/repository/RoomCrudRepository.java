package com.pay.repository;

import com.pay.entity.Room;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoomCrudRepository extends CrudRepository<Room, Long> {
    Optional<Room> findByRoomId(String roomId);
}
