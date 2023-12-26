package de.pet_project.service;

import de.pet_project.convertor.TicketDtoConvert;
import de.pet_project.domain.Game;
import de.pet_project.dto.ticket.TicketCreateDTO;
import de.pet_project.dto.ticket.TicketReadDTO;
import de.pet_project.dto.ticket.TicketUpdateDTO;
import de.pet_project.domain.TicketOrder;
import de.pet_project.repository.GameRepository;
import de.pet_project.repository.TicketOrdersRepository;
import de.pet_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TicketOrdersService {

    private final TicketDtoConvert ticketDtoConvert;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;

    private final TicketOrdersRepository ticketOrdersRepository;

    public Page<TicketReadDTO> findByPage(Pageable pageable) {
        return ticketOrdersRepository.findAll(pageable).map(ticketDtoConvert::convertToTicketReadDTO);
    }

    @Transactional
    public TicketReadDTO save(UserDetails userDetails, Integer gameId) {
        Game game = gameRepository.findById(gameId).orElseThrow();
        TicketOrder ticketOrder = new TicketOrder();
        ticketOrder.setState(TicketOrder.OrderState.NEW);
        ticketOrder.setCreateAt(LocalDateTime.now());
        ticketOrder.setPrice(game.getPrice());
        ticketOrder.setUser(userRepository.findByNickname(userDetails.getUsername())
                .orElseThrow());
        TicketOrder save = ticketOrdersRepository.save(ticketOrder);
        return ticketDtoConvert.convertToTicketReadDTO(save);
    }

    public TicketReadDTO findById(Integer id) {
        return ticketDtoConvert.convertToTicketReadDTO(ticketOrdersRepository.findById(id).orElseThrow());
    }

    @Transactional
    public TicketReadDTO update(TicketUpdateDTO ticketOrdersUpdateDTO) {
        TicketOrder hasTicket = ticketOrdersRepository.findById(ticketOrdersUpdateDTO.getId()).orElseThrow();
        TicketOrder ticketOrder = ticketDtoConvert.convertToTicketOrder(ticketOrdersUpdateDTO);
        ticketOrder.setCreateAt(hasTicket.getCreateAt());
        ticketOrdersRepository.save(ticketOrder);
        return ticketDtoConvert.convertToTicketReadDTO(ticketOrder);
    }

    public TicketReadDTO cansel(Integer id) {
        Optional<TicketOrder> ticketOrder = ticketOrdersRepository.findById(id);
        ticketOrder.ifPresent(t -> {
            t.setState(TicketOrder.OrderState.CANCELED);
            ticketOrdersRepository.save(t);
        });

        return ticketDtoConvert.convertToTicketReadDTO(ticketOrder.get());
    }
}
