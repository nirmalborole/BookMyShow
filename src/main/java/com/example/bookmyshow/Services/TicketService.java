package com.example.bookmyshow.Services;

import com.example.bookmyshow.models.Ticket;

import java.util.List;

public interface TicketService {
    Ticket bookTicket(int userId, int showId, List<Integer> showSeatIds) throws Exception;
}
