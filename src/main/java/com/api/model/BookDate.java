package com.api.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class BookDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @DateTimeFormat(pattern = "YYYY-DD-MM")
    private Date startDate;
    @DateTimeFormat(pattern = "YYYY-DD-MM")
    private Date endDate;
}
