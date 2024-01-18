package de.pet_project.domain.post;

import de.pet_project.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reaction_to_post_commit")
public class ReactionToPostCommit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    CommitForPost commitId;
    @ManyToOne
    User userId;
    String reaction;
    LocalDateTime created_at;
}
