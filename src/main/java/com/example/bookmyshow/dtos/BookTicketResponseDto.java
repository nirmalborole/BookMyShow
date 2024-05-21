package com.example.bookmyshow.dtos;

import com.example.bookmyshow.models.Ticket;
import lombok.Data;

@Data
public class BookTicketResponseDto {
    private Response response;
    private Ticket ticket;
}
