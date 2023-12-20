package de.pet_project.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

     enum State {
        NOT_CONFIRM,
        CONFIRMED,
        DELETED,
        BANNED
    }
    enum Role{
        ADMIN,
        USER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;
    private String avatar;
    @Column(unique = true)
    private String nickname;
    private String firstname;
    private String lastname;
    @Column(name = "birth_date")
    private LocalDate birthDate;
    @Email
    @Column(nullable = false,unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    private String phone;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private State state;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

   @OneToMany(mappedBy = "user")
    private List<TicketOrders> orders;

    public User(String nickname, String email, String password, State state) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.state = state;
    }
}
