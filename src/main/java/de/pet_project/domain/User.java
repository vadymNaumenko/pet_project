package de.pet_project.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User implements UserDetails {

    public enum State {
        NOT_CONFIRM,
        CONFIRMED,
        DELETED,
        BANNED
    }

    public enum Role implements GrantedAuthority {
        ADMIN,
        USER;

        @Override
        public String getAuthority() {
            return name();
        }
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
    @Column(nullable = false, unique = true)
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
    private List<TicketOrder> orders; //todo make method get all orders from user(id)

    public User(String nickname, String email, String password, State state, LocalDateTime createdAt, Role role) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.state = state;
        this.createdAt = createdAt;
        this.role = role;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !state.equals(State.BANNED);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return state.equals(State.CONFIRMED);
    }

}
