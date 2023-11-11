package de.pet_project.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "email",unique = true)
    private String email;

    private String password;

    private String firstname;

    private String lastname;

    private String birth_date;

    @OneToOne(fetch = FetchType.EAGER,mappedBy = "user")
    private Image avatar;

    @Enumerated(EnumType.STRING)
    private Role role;

    private LocalDate dateOfCreat;

}

