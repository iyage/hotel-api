package com.api.exceptions;

import com.api.model.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class handleBadRequestError {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        System.out.println(errors);
        return errors;
    }


    @ExceptionHandler(ResourceNotFoundExption.class)
    public ResponseEntity<ResponseDto> handleResourceNotFoundException
            (ResourceNotFoundExption ex, WebRequest request) {
        ResponseDto  responseDto = new ResponseDto(ex.getMessage(), "fail",null,request.getDescription(false),new Date());
        return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceExistException.class)
    public ResponseEntity<ResponseDto> handleResourceExistException
            (ResourceExistException ex, WebRequest request) {
        ResponseDto  responseDto = new ResponseDto(ex.getMessage(), "fail",null,request.getDescription(false),new Date());
        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }
}
