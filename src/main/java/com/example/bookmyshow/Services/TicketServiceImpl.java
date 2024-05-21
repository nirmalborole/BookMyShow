package com.example.bookmyshow.Services;

import com.example.bookmyshow.Exceptions.InvalideBookTicketRequestException;
import com.example.bookmyshow.Exceptions.SeatUnAvailableException;
import com.example.bookmyshow.models.*;
import com.example.bookmyshow.repositories.ShowRepoitory;
import com.example.bookmyshow.repositories.ShowSeatRepositoty;
import com.example.bookmyshow.repositories.TicketRepository;
import com.example.bookmyshow.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService{
    private UserRepository userRepository;
    private ShowRepoitory showRepoitory;
    private ShowSeatRepositoty showSeatRepositoty;
    private TicketRepository ticketRepository;

    @Autowired
    public TicketServiceImpl(UserRepository userRepository, ShowRepoitory showRepoitory, ShowSeatRepositoty showSeatRepositoty, TicketRepository ticketRepository) {
        this.userRepository = userRepository;
        this.showRepoitory = showRepoitory;
        this.showSeatRepositoty = showSeatRepositoty;
        this.ticketRepository = ticketRepository;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public Ticket bookTicket(int userId, int showId, List<Integer> showSeatIds) throws Exception {
        Optional<User> userOptional = this.userRepository.findById(userId);
        User user;
        if(userOptional.isPresent()){
            user=userOptional.get();
        }else{
            throw new InvalideBookTicketRequestException("user not present in db");
        }

        Show show = this.showRepoitory.findById(showId).orElseThrow(() -> new InvalideBookTicketRequestException("show id not valid"));
        ShowSeat showSeat = this.showSeatRepositoty.findById(showSeatIds.get(0)).orElseThrow(() -> new InvalideBookTicketRequestException("seat id not valide"));
        if(showSeat.getShow().getId() != showId){
            throw new InvalideBookTicketRequestException("given seat not belong to same show");
        }

        List<ShowSeat> showSeats = this.showSeatRepositoty.findShowSeatByIdInAndSeatStatus_AvailableAndShow(showSeatIds, show);
        if(showSeats.size() != showSeatIds.size()){
            throw new SeatUnAvailableException("some seat are not avalable");
        }
        for(ShowSeat ss: showSeats){
            ss.setBookedBy(user);
            ss.setSeatStatus(SeatStatus.BLOCKED);
        }
        showSeatRepositoty.saveAll(showSeats);

        Ticket ticket= new Ticket();
        ticket.setMovie(show.getMovie());
        ticket.setShow(show);
        ticket.setShowSeats(showSeats);
        ticket.setUser(user);

        return ticketRepository.save(ticket);
    }
}
