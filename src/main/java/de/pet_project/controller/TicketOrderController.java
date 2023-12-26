package de.pet_project.controller;

import de.pet_project.dto.ticket.TicketReadDTO;
import de.pet_project.dto.ticket.TicketUpdateDTO;
import de.pet_project.service.TicketOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class TicketOrderController {
    private final TicketOrderService ticketOrdersService;

    @GetMapping
    public Page<TicketReadDTO> findByPage(Pageable pageable){
        return ticketOrdersService.findByPage(pageable);
    }

    @PostMapping("/game/{id}")
    public ResponseEntity<TicketReadDTO> create(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Integer id
                                  ){
        System.out.println();
         //todo add gmail message for user
//         return ticketOrdersService.save(userDetails,id);
        return  ResponseEntity.status(HttpStatus.CREATED).body(ticketOrdersService.save(userDetails,id));
    }

    @PutMapping()
    public ResponseEntity <TicketReadDTO> update(@RequestBody TicketUpdateDTO ticketOrdersUpdateDTO){
        return ResponseEntity.status(HttpStatus.OK).body(ticketOrdersService.update(ticketOrdersUpdateDTO));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<TicketReadDTO> cansel(@PathVariable Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(ticketOrdersService.cancel(id));
    }
}
