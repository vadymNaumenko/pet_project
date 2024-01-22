package de.pet_project.service.integration.service;

import de.pet_project.domain.TicketOrder;
import de.pet_project.dto.ticket.TicketReadDTO;
import de.pet_project.service.GameService;
import de.pet_project.service.TicketOrderService;
import de.pet_project.service.integration.annotation.IT;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.*;

@IT
@RequiredArgsConstructor
public class TicketOrderServiceIT {

    private final TicketOrderService ticketOrderService;
    private final GameService gameService;

    @Test
//    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void findByPage() {
        PageRequest pageable = PageRequest.of(0, 3, Sort.sort(TicketOrder.class));
        Page<TicketReadDTO> actual = ticketOrderService
                .findByPage(pageable);
        assertTrue(actual.hasContent());

        assertEquals(3,actual.getContent().size());
        assertEquals(0,pageable.getPageNumber());
        System.out.println(pageable.getPageSize());
        assertEquals(3,pageable.getPageSize());

        pageable = pageable.next();
        actual = ticketOrderService
                .findByPage(pageable);

        assertTrue(actual.hasContent());
        assertEquals(1,pageable.getPageNumber());
        assertEquals(3,actual.getContent().size());
        assertEquals(3,pageable.getPageSize());
    }

//    void save(){
//        gameService.findByTitle()
//
//    }
//
//
//
//    @Transactional
//    public TicketReadDTO save(String email, Integer gameId) {
//        log.info("Executing save method");
//
//        Game game = gameRepository.findById(gameId).orElseThrow();
//        TicketOrder ticketOrder = new TicketOrder();
//        ticketOrder.setState(TicketOrder.OrderState.NEW);
//        ticketOrder.setCreateAt(LocalDateTime.now());
//        ticketOrder.setPrice(game.getPrice());
//        ticketOrder.setGame(game);
//        ticketOrder.setUser(userRepository.findByEmail(email)
//                .orElseThrow());
//
//        TicketOrder save = ticketOrdersRepository.save(ticketOrder);
//        log.info("TicketOrder saved with id: {}", save.getId());
//
//        templateMailSender.sendTicket(ticketOrder);
//
//        return ticketDtoConvert.convertToTicketReadDTO(save);
//    }
//
//    public Page<TicketReadDTO> findAllByUser(Integer id, Pageable pageable) {
//        return ticketOrdersRepository.findAllTicketOrderByUserId(id, pageable)
//                .map(ticketDtoConvert::convertToTicketReadDTO);
//    }
    //    public ResponseEntity<?> save(Integer id) {
//
//
//        Optional<TicketOrder> order = ticketOrdersRepository.findById(id);
//        if (order.isPresent()) {
//            order.get().setState(TicketOrder.OrderState.PAID);
//            ticketOrdersRepository.save(order.get());
//            log.info("TicketOrder update state: {}, and save with id: {}"
//                    , order.get().getState(), order.get().getId());
//            return ResponseEntity.status(HttpStatus.OK)
//                    .body(ticketDtoConvert.convertToTicketReadDTO(order.get()));
//        }
//
//        log.info("TicketOrder not found order with id: {}"
//                , order.get().getId());
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new TicketOrder());
//    }
//
//    @Transactional
//    public TicketReadDTO cancel(Integer id) {
//        log.info("Executing cancel method");
//
//        Optional<TicketOrder> ticketOrder = ticketOrdersRepository.findById(id);
//        ticketOrder.ifPresent(t -> {
//            t.setState(TicketOrder.OrderState.CANCELED);
//            ticketOrdersRepository.save(t);
//            log.info("TicketOrder canceled with id: {}", t.getId());
//        });
//
//        return ticketDtoConvert.convertToTicketReadDTO(ticketOrder.orElseThrow(() ->
//                new NoSuchElementException("Not found ticket with id: " + id)));
//
//    }
//
//    public Optional<TicketReadDTO> findById(Integer id) {
//        return ticketOrdersRepository.findById(id).map(ticketDtoConvert::convertToTicketReadDTO);
//    }
//
//    public boolean hasOrder(Integer orderId) {
//        return ticketOrdersRepository.existsById(orderId);
//    }
//
//    //    @PreAuthorize("hasRole('ADMIN')")
//    public UserReadDTO findUserByTicketId(Integer id) {
//        return ticketOrdersRepository.findById(id).map(TicketOrder::getUser)
//                .map(userDtoConvert::convertToUserReadDto).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
//    }
}
