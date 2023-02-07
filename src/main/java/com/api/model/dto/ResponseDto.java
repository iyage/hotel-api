package com.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResponseDto {
    private String message;
    private String status;
    Object data = null;
    String path;
    private Date timeStamp;
}
