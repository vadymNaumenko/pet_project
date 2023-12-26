package de.pet_project.service;

import de.pet_project.convertor.TicketDtoConvert;
import de.pet_project.domain.Game;
import de.pet_project.dto.ticket.TicketReadDTO;
import de.pet_project.dto.ticket.TicketUpdateDTO;
import de.pet_project.domain.TicketOrder;
import de.pet_project.repository.GameRepository;
import de.pet_project.repository.TicketOrdersRepository;
import de.pet_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class TicketOrdersService {

    private final TicketDtoConvert ticketDtoConvert;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private final TicketOrdersRepository ticketOrdersRepository;


    public Page<TicketReadDTO> findByPage(Pageable pageable) {
        log.info("Executing findByPage method");
        return ticketOrdersRepository.findAll(pageable).map(ticketDtoConvert::convertToTicketReadDTO);
    }

    @Transactional
    public TicketReadDTO save(UserDetails userDetails, Integer gameId) {
        log.info("Executing save method");

        Game game = gameRepository.findById(gameId).orElseThrow();
        TicketOrder ticketOrder = new TicketOrder();
        ticketOrder.setState(TicketOrder.OrderState.NEW);
        ticketOrder.setCreateAt(LocalDateTime.now());
        ticketOrder.setPrice(game.getPrice());
        ticketOrder.setUser(userRepository.findByNickname(userDetails.getUsername())
                .orElseThrow());

        TicketOrder save = ticketOrdersRepository.save(ticketOrder);
        log.info("TicketOrder saved with id: {}", save.getId());

//        return ticketDtoConvert.convertToTicketReadDTO(save);
        return new TicketReadDTO();
    }

    public TicketReadDTO findById(Integer id) {
        log.info("Executing findById method");

//        return ticketDtoConvert.convertToTicketReadDTO(ticketOrdersRepository.findById(id).orElseThrow());
        Optional<TicketOrder> ticketOrderOptional = ticketOrdersRepository.findById(id);
        TicketOrder ticketOrder = ticketOrderOptional.orElseThrow(() -> new RuntimeException("TicketOrder not found with id: " + id));
        log.info("Found TicketOrder with id: {}", ticketOrder.getId());
        return ticketDtoConvert.convertToTicketReadDTO(ticketOrder);
    }

    @Transactional
    public TicketReadDTO update(TicketUpdateDTO ticketOrdersUpdateDTO) {
        log.info("Executing update method");

        TicketOrder hasTicket = ticketOrdersRepository.findById(ticketOrdersUpdateDTO.getId()).orElseThrow();
        TicketOrder ticketOrder = ticketDtoConvert.convertToTicketOrder(ticketOrdersUpdateDTO);
        ticketOrder.setCreateAt(hasTicket.getCreateAt());

        ticketOrdersRepository.save(ticketOrder);
        log.info("TicketOrder updated with id: {}", ticketOrder.getId());

        return ticketDtoConvert.convertToTicketReadDTO(ticketOrder);
    }

    public TicketReadDTO cancel(Integer id) {
        log.info("Executing cancel method");

        Optional<TicketOrder> ticketOrder = ticketOrdersRepository.findById(id);
        ticketOrder.ifPresent(t -> {
            t.setState(TicketOrder.OrderState.CANCELED);
            ticketOrdersRepository.save(t);
            log.info("TicketOrder canceled with id: {}", t.getId());
        });

        return ticketDtoConvert.convertToTicketReadDTO(ticketOrder.orElseThrow());
    }
}
