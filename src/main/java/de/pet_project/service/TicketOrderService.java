package de.pet_project.service;

import de.pet_project.convertor.TicketDtoConvert;
import de.pet_project.convertor.UserDtoConvert;
import de.pet_project.domain.Game;
import de.pet_project.dto.ticket.TicketReadDTO;
import de.pet_project.domain.TicketOrder;
import de.pet_project.dto.user.UserReadDTO;
import de.pet_project.mail.TemplateMailSender;
import de.pet_project.repository.GameRepository;
import de.pet_project.repository.TicketOrdersRepository;
import de.pet_project.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TicketOrderService {

    private final TicketDtoConvert ticketDtoConvert;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private final TicketOrdersRepository ticketOrdersRepository;
    private final UserDtoConvert userDtoConvert;
    private final TemplateMailSender templateMailSender;


    public Page<TicketReadDTO> findByPage(Pageable pageable) {
        log.info("Executing findByPage method");
        Page<TicketOrder> result = ticketOrdersRepository.findAll(pageable);
        return result.map(ticketDtoConvert::convertToTicketReadDTO);
    }

    @Transactional
    public ResponseEntity<?> save(Integer id) {
        log.info("Executing save method");

        Optional<TicketOrder> order = ticketOrdersRepository.findById(id);
        if (order.isPresent()) {
            order.get().setState(TicketOrder.OrderState.PAID);
            ticketOrdersRepository.save(order.get());
            log.info("TicketOrder update state: {}, and save with id: {}"
                    , order.get().getState(), order.get().getId());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ticketDtoConvert.convertToTicketReadDTO(order.get()));
        }

        log.info("TicketOrder not found order with id: {}"
                , order.get().getId());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new TicketOrder());
    }

    @Transactional
    public TicketReadDTO save(String email, Integer gameId) {
        log.info("Executing save method");

        Game game = gameRepository.findById(gameId).orElseThrow();
        TicketOrder ticketOrder = new TicketOrder();
        ticketOrder.setState(TicketOrder.OrderState.NEW);
        ticketOrder.setCreateAt(LocalDateTime.now());
        ticketOrder.setPrice(game.getPrice());
        ticketOrder.setGame(game);
        ticketOrder.setUser(userRepository.findByEmail(email)
                .orElseThrow());
        //todo add generate number ticket
        TicketOrder save = ticketOrdersRepository.save(ticketOrder);
        log.info("TicketOrder saved with id: {}", save.getId());

        templateMailSender.sendTicket(ticketOrder);

        return ticketDtoConvert.convertToTicketReadDTO(save);
    }

    public Page<TicketReadDTO> findAllByUser(Integer id, Pageable pageable) {
        return ticketOrdersRepository.findAllTicketOrderByUserId(id, pageable)
                .map(ticketDtoConvert::convertToTicketReadDTO);
    }

    @Transactional
    public TicketReadDTO cancel(Integer id) {
        log.info("Executing cancel method");

        Optional<TicketOrder> ticketOrder = ticketOrdersRepository.findById(id);
        ticketOrder.ifPresent(t -> {
            t.setState(TicketOrder.OrderState.CANCELED);
            ticketOrdersRepository.save(t);
            log.info("TicketOrder canceled with id: {}", t.getId());
        });

        return ticketDtoConvert.convertToTicketReadDTO(ticketOrder.orElseThrow(() ->
                new NoSuchElementException("Not found ticket with id: " + id)));

    }

    public Optional<TicketReadDTO> findById(Integer id) {
        return ticketOrdersRepository.findById(id).map(ticketDtoConvert::convertToTicketReadDTO);
    }

    public boolean hasOrder(Integer orderId) {
        return ticketOrdersRepository.existsById(orderId);
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    public UserReadDTO findUserByTicketId(Integer id) {
        return ticketOrdersRepository.findById(id).map(TicketOrder::getUser)
                .map(userDtoConvert::convertToUserReadDto).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
