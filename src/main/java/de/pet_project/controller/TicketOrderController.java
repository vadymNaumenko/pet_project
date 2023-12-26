package de.pet_project.controller;

import de.pet_project.dto.ticket.TicketCreateDTO;
import de.pet_project.dto.ticket.TicketReadDTO;
import de.pet_project.dto.ticket.TicketUpdateDTO;
import de.pet_project.domain.TicketOrder;
import de.pet_project.service.TicketOrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class TicketOrderController {
    private final TicketOrdersService ticketOrdersService;

    @GetMapping
    public Page<TicketReadDTO> findByPage(Pageable pageable){
        return ticketOrdersService.findByPage(pageable);
    }

    @PostMapping("/game/{id}")
    public TicketReadDTO create(@AuthenticationPrincipal UserDetails userDetails,@PathVariable Integer id
                                  ){

         //todo add gmail message for user
         return ticketOrdersService.save(userDetails,id);
    }

    @PutMapping()
    public TicketReadDTO update(@RequestBody TicketUpdateDTO ticketOrdersUpdateDTO){
        return ticketOrdersService.update(ticketOrdersUpdateDTO);

    }
    @DeleteMapping("/{id}")
    public TicketReadDTO cansel(@PathVariable Integer id){
        return ticketOrdersService.cansel(id);
    }
}
