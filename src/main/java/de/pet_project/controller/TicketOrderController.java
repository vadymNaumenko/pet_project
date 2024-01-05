package de.pet_project.controller;

import de.pet_project.dto.ticket.TicketReadDTO;
import de.pet_project.dto.user.UserReadDTO;
import de.pet_project.service.TicketOrderService;
import lombok.RequiredArgsConstructor;
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
public class TicketOrderController {
    private final TicketOrderService ticketOrdersService;

    @GetMapping
    public Page<TicketReadDTO> findByPage(Pageable pageable) {
        return ticketOrdersService.findByPage(pageable);
    }

    @GetMapping("/{id}")
    public TicketReadDTO findById(@PathVariable Integer id) {
        return ticketOrdersService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}/user")
    public UserReadDTO findUserByTicket(@PathVariable Integer id) {
        return ticketOrdersService.findUserByTicketId(id);
    }

    @GetMapping("/buy/{orderID}")
    public ResponseEntity<?> buyGame(@PathVariable Integer orderID) {
        if (ticketOrdersService.hasOrder(orderID)) {
            ticketOrdersService.save(orderID);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Order not found");
    }

    @PostMapping("/game/{id}")
    public ResponseEntity<TicketReadDTO> create(@AuthenticationPrincipal UserDetails userDetails,
                                                @PathVariable Integer id) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ticketOrdersService.save(userDetails.getUsername(), id));
    }

    //todo add method update ticket
    @GetMapping("/user/{id}")
    public Page<TicketReadDTO> findAllByUser(@PathVariable Integer id, Pageable pageable) {
        return ticketOrdersService.findAllByUser(id, pageable);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TicketReadDTO> cansel(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(ticketOrdersService.cancel(id));
    }

}
