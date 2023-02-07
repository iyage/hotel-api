package com.api.service.serviceImpl;

import com.api.exceptions.ResourceNotFoundExption;
import com.api.model.Hotel;
import com.api.model.Room;
import com.api.model.dto.HotelDto;
import com.api.repository.HotelRepository;
import com.api.repository.RoomRepository;
import com.api.service.HotelService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class HotelServiceImpl implements HotelService {
    @Autowired
    HotelRepository hotelRepository;
@Autowired
RoomRepository roomRepository;
    @Override
    public Hotel addNewHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    @Override
    public void deleteHotel(long id) {
      Hotel hotel = hotelRepository.findById(id).orElseThrow(()->new ResourceNotFoundExption("Hotel does not exist"));
      hotelRepository.delete(hotel);
    }

    @Transactional
    @Override
    public void addRoom(long roomId, long hotelId) {
     Room room = roomRepository.findById(roomId).orElseThrow(()->new ResourceNotFoundExption("Room does not exist"));
     Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(()-> new ResourceNotFoundExption("Hotel does not exist"));
     hotel.getRooms().add(room);
    }

    @Transactional
    @Override
    public void removeRoom(long roomId, long hotelId) {
        Room room = roomRepository.findById(roomId).orElseThrow(()->new ResourceNotFoundExption("Room does not exist"));
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(()-> new ResourceNotFoundExption("Hotel does not exist"));
        hotel.getRooms().remove(room);
    }

    @Override
    public List<Hotel> fetchAllHotel() {
        return hotelRepository.findAll();
    }

    @Override
    public Hotel getHotel(long id) {
        return  hotelRepository.findById(id).orElseThrow(()-> new ResourceNotFoundExption(String.format("Hotel with %d id does not exist",id)));
    }

    @Override
    public void updateHotel(Hotel hotel) {

    }
}
