package com.api.controller;
import com.api.model.Hotel;
import com.api.model.dto.ResponseDto;
import com.api.service.serviceImpl.HotelServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestController
public class HotelController {
    @Autowired
    HotelServiceImpl hotelService;
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/new_hotel")
    public ResponseEntity<ResponseDto> addNewHotel(@RequestBody Hotel hotel, WebRequest request)
    {
        hotelService.addNewHotel(hotel);
        ResponseDto responseDto = new ResponseDto(
                "success","201",hotelService.addNewHotel(hotel),
                request.getDescription(false),new Date() );
        return  new  ResponseEntity<>(responseDto,HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/remove_room/{roomId}/{hotelId}")
    public ResponseEntity<ResponseDto> removeRoom(@PathVariable(value="roomId") long roomId,@PathVariable(value="hotelId")  long hotelId,WebRequest request)
    {
        hotelService.removeRoom( roomId,hotelId);
        ResponseDto responseDto = new ResponseDto(
                "success","200",null,
                request.getDescription(false),new Date() );
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/delete_hotel/{id}")
    public ResponseEntity<ResponseDto> deleteHotel(@PathVariable(value = "id")  long id,WebRequest request) {
     hotelService.deleteHotel(id);
    ResponseDto responseDto = new ResponseDto(
            "success","200",null,
            request.getDescription(false),new Date() );
     return  new ResponseEntity<>(responseDto,HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
@GetMapping("/add_room_to_hotel/{roomId}/{hotelId}")
    public ResponseEntity<ResponseDto> addRoom(@PathVariable(value = "roomId") long roomId,  @PathVariable(value = "hotelId") long hotelId,WebRequest request) {
            hotelService.addRoom(roomId,hotelId);
    ResponseDto responseDto = new ResponseDto(
            "success","200",null,
            request.getDescription(false),new Date() );
    return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_CLIENT')")
    @GetMapping("/get_all_Hotel")
    public ResponseEntity<ResponseDto> fetchAllHotel(WebRequest request) {
        ResponseDto responseDto = new ResponseDto(
                "success","200",hotelService.fetchAllHotel(),
                request.getDescription(false),new Date() );
        return  new ResponseEntity<>(responseDto,HttpStatus.OK) ;
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_CLIENT')")
 @GetMapping("/get_hotel/{id}")
    public ResponseEntity<ResponseDto> getHotel(@PathVariable (value = "id") long id,WebRequest request) {
     ResponseDto responseDto = new ResponseDto(
             "success","200",hotelService.getHotel(id),
             request.getDescription(false),new Date() );

        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }

}
