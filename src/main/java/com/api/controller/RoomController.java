package com.api.controller;

import com.api.config.S3Util;
import com.api.model.Photos;
import com.api.model.Room;
import com.api.model.dto.BookingDto;
import com.api.model.dto.ResponseDto;
import com.api.service.serviceImpl.RoomServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import java.util.Date;



@RestController
public class RoomController {
@Autowired
    RoomServiceImpl roomService;
    @Autowired
    S3Util s3Util;
 @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/add_new_room")
    public ResponseEntity<ResponseDto> addNewRoom(@RequestBody  Room room,WebRequest request) {
        ResponseDto responseDto = new ResponseDto(
                "success","201",roomService.addNewRoom(room),
                request.getDescription(false),new Date() );

        return  new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  @GetMapping("/delete_room/{id}")
    public ResponseEntity<String> deleteRoom(long id) {
    return  new ResponseEntity<>("Room successfully deleted",HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_CLIENT')")
    @PostMapping("/book_a_room")
    public ResponseEntity<ResponseDto> addBookDate( @RequestBody BookingDto bookingDto,WebRequest request) {
           roomService.addBookDate(bookingDto);
           ResponseDto responseDto = new ResponseDto(
                   "success","200",null,
                   request.getDescription(false),new Date() );
           return  new ResponseEntity<>(responseDto,HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_CLIENT')")
 @GetMapping("/cancel_book_date/{roomId}/{bookDateId}")
    public  ResponseEntity<ResponseDto> removeBookDate(@PathVariable long roomId, long bookDateId, WebRequest request) {
           roomService.removeBookDate(roomId,bookDateId);
     ResponseDto responseDto = new ResponseDto("success","200",null,request.getDescription(false),new Date());
           return  new ResponseEntity<>(responseDto,HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_CLIENT')")
    @GetMapping("/get_all_rooms")
    public ResponseEntity<ResponseDto> fetchAllRoom(WebRequest request) {

        ResponseDto responseDto = new ResponseDto("success","200",roomService.fetchAllRoom(),request.getDescription(false),new Date());
        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_CLIENT')")
    @GetMapping("/get_room/{id}")
    public ResponseEntity<ResponseDto> getRoom(@PathVariable(value = "id") long id,WebRequest request) {
        ResponseDto responseDto = new ResponseDto("success","200",roomService.getRoom(id),request.getDescription(false),new Date());
        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/upload/{roomId}")
    public ResponseEntity<ResponseDto> handleUploadForm(@PathVariable(value = "roomId") long roomId, @RequestParam("file") MultipartFile multipart, WebRequest request) {
        String fileName = multipart.getOriginalFilename();
        String photoUrl = "";
        System.out.println(roomId);


        try {
            photoUrl =s3Util.uploadFile(fileName, multipart.getInputStream());
            Photos photo = new Photos();
            photo.setUrl(photoUrl);
            Room room = roomService.addPhotoToRoom(photo,roomId);

            return  new ResponseEntity<>(new ResponseDto("success","200",room,request.getDescription(false),new Date()),HttpStatus.OK);
        } catch (Exception ex) {

            return  new ResponseEntity<>(new ResponseDto("fail","500",ex.getMessage(),request.getDescription(false),new Date()),HttpStatus.BAD_REQUEST);
        }

    }
}
