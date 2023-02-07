package com.api.service;
import com.api.model.Photos;
import com.api.model.Room;
import com.api.model.dto.BookingDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomService {
    public Room addNewRoom(Room room);
    public void deleteRoom(long id);
    public void addBookDate(BookingDto bookingDto);
    public void removeBookDate(long roomId, long bookDateId);
    public List<Room> fetchAllRoom();
    public Room getRoom(long id);
   public  Room addPhotoToRoom(Photos photo, long id);
//    public void updateHotel(Hotel hotel);
}
