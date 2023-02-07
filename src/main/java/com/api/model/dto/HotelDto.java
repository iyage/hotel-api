package com.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelDto {
    private String name;
    private String type;
    private String address;
    private String city;
    private String  distance;
    private String Description;
    private int rating;
}
