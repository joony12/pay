package com.pay.service.room;

import com.pay.entity.Room;
import com.pay.repository.RoomCrudRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomCrudRepository roomCrudRepository;

    @Override
    @Transactional
    public Room createRoom(Long userId, String roomId) {
        Room room = new Room();
        room.setRoomId(roomId);
        return roomCrudRepository.save(room);
    }
}
