package de.pet_project.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ticket_orders")
public class TicketOrders {
    enum OrderState {
        NEW, SENT, PAID,
        DELIVERED, CANCELED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String number;
    private double price;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private OrderState state;
    @Column(name = "created_at")
    private LocalDateTime createAt;


}
