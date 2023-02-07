package com.api.service;

import com.api.model.Hotel;
import com.api.model.Room;
import com.api.model.dto.HotelDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HotelService {
    public Hotel addNewHotel(Hotel hotelDto);
    public void deleteHotel(long id);
    public void addRoom(long roomId, long hotelId);
    public void removeRoom(long roomId, long hotelId);
    public List<Hotel>  fetchAllHotel();
    public Hotel getHotel(long id);
    public void updateHotel(Hotel hotel);
}
