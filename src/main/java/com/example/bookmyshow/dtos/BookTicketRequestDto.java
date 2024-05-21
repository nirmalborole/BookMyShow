package com.example.bookmyshow.dtos;

import lombok.Data;

import java.util.List;
@Data
public class BookTicketRequestDto {
    private int userId;
    private int showId;
    private List<Integer> showSeatIds;
}
