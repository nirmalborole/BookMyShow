package com.example.bookmyshow.Controller;

import com.example.bookmyshow.Exceptions.InvalideBookTicketRequestException;
import com.example.bookmyshow.Services.TicketService;
import com.example.bookmyshow.dtos.BookTicketRequestDto;
import com.example.bookmyshow.dtos.BookTicketResponseDto;
import com.example.bookmyshow.dtos.Response;
import com.example.bookmyshow.models.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.swing.*;
@Controller
public class TicketController {

    private TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @RequestMapping(path= "/bookTicket")
    public BookTicketResponseDto bookTicket(BookTicketRequestDto requestDto){
        BookTicketResponseDto responseDto=new BookTicketResponseDto();
        try{
            validatebookTicketRequest(requestDto);
            Ticket ticket = ticketService.bookTicket((requestDto.getUserId()), requestDto.getShowId(), requestDto.getShowSeatIds());
            Response response=Response.getSuccessResponse();
            responseDto.setResponse(response);
            responseDto.setTicket(ticket);

        }catch (Exception e){
            Response response=Response.getFailuerResponse(e.getMessage());
            responseDto.setResponse(response);
        }
        return responseDto;
    }

    public static void validatebookTicketRequest(BookTicketRequestDto requestDto) throws InvalideBookTicketRequestException {
        if(requestDto.getShowId()<=0){
            throw new InvalideBookTicketRequestException("show id cannot be negative or zero");
        }
        if(requestDto.getUserId() <=0){
            throw new InvalideBookTicketRequestException("user id cannot be negative or zero");
        }
        if(requestDto.getShowSeatIds() == null || requestDto.getShowSeatIds().isEmpty()){
            throw new InvalideBookTicketRequestException("shows id cannot be negative or zero");
        }
    }
}
