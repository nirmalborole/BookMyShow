package com.example.bookmyshow.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name="seat_type_shows")
public class SeatTypeShow extends BaseModel{
    private SeatType seatType;
    @ManyToOne
    private Show show;
    private double price;

}
