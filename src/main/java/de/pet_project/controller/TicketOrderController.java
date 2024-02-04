package de.pet_project.controller;

import de.pet_project.dto.ticket.TicketReadDTO;
import de.pet_project.dto.user.UserReadDTO;
import de.pet_project.service.TicketOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
@Slf4j
public class TicketOrderController {
    private final TicketOrderService ticketOrdersService;
    @GetMapping
    public Page<TicketReadDTO> findByPage(Pageable pageable, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("Fetching ticket orders for user: {}", userDetails.getUsername());
        if (userDetails == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ticketOrdersService.findAllByUserEmail(userDetails.getUsername(), pageable);
    }

    @GetMapping("/{id}")
    public TicketReadDTO findById(@PathVariable Integer id) {
        log.info("Fetching ticket order with ID: {}", id);
        return ticketOrdersService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}/user")
    public UserReadDTO findUserByTicket(@PathVariable Integer id) {
        log.info("Fetching user for ticket order with ID: {}", id);
        return ticketOrdersService.findUserByTicketId(id);
    }

    @PostMapping("/game{id}")
    public ResponseEntity<TicketReadDTO> create(@AuthenticationPrincipal UserDetails userDetails,
                                                @PathVariable Integer id) {
        log.info("Creating ticket order for user: {} and game ID: {}", userDetails.getUsername(), id);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ticketOrdersService.save(userDetails.getUsername(), id));
    }

    @GetMapping("/user/{id}")
    public Page<TicketReadDTO> findAllByUser(@PathVariable Integer id, Pageable pageable) {
        log.info("Fetching ticket orders for user with ID: {}", id);
        return ticketOrdersService.findAllByUser(id, pageable);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TicketReadDTO> cancel(@PathVariable Integer id) {
        log.info("Canceling ticket order with ID: {}", id);
        return ResponseEntity.status(HttpStatus.OK).body(ticketOrdersService.cancel(id));
    }
}
