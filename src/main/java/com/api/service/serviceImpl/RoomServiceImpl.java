package com.api.service.serviceImpl;

import com.api.exceptions.ResourceNotFoundExption;
import com.api.model.BookDate;
import com.api.model.Photos;
import com.api.model.Room;
import com.api.model.dto.BookingDto;
import com.api.repository.BookDateRepository;
import com.api.repository.RoomRepository;
import com.api.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    BookDateRepository bookDateRepository;

    @Override
    public Room addNewRoom(Room room) {
        return  roomRepository.save(room);
    }

    @Override
    public void deleteRoom(long id) {

    }
@Transactional
    @Override
    public void addBookDate(BookingDto bookingDto) {
        Room room = roomRepository.findById(bookingDto.getRoomId()).orElseThrow(()->new ResourceNotFoundExption("Room does not exist"));
      BookDate bookDate = new BookDate();
      bookDate.setEndDate(bookingDto.getEndDate());
      bookDate.setStartDate(bookingDto.getStartDate());
      room.getUnavailableDate().add(bookDate);
    }

    @Transactional
    @Override
    public void removeBookDate(long roomId, long bookDateId) {
        Room room = roomRepository.findById(roomId).orElseThrow(()->new ResourceNotFoundExption("Room does not exist"));
        BookDate bookDate = bookDateRepository.findById(bookDateId).orElseThrow(()->new ResourceNotFoundExption("Book not fixed"));
        room.getUnavailableDate().remove(bookDate);
    }

    @Override
    public List<Room> fetchAllRoom() {
        return  roomRepository.findAll();
    }

    @Override
    public Room getRoom(long id) {
        return roomRepository.findById(id).orElseThrow(()->new ResourceNotFoundExption("Room does not exist"));
    }

@Transactional
    @Override
    public Room addPhotoToRoom(Photos photo, long id) {
        Room room = roomRepository.findById(id).orElseThrow(()-> new ResourceNotFoundExption("id not found"));
         room.getUrl().add(photo);

        return room;
    }
}
