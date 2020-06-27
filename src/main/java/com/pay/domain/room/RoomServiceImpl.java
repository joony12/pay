package com.pay.domain.room;

import com.pay.infra.db.RoomCrudRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomCrudRepository roomCrudRepository;

    @Override
    public Room createRoom(Room room) {
        return roomCrudRepository.save(room);
    }
}
