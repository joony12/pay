package com.pay.service.room;

import com.pay.entity.Room;

public interface RoomService {
    Room createRoom(Long userId, String roomId);
}
