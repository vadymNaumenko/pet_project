package de.pet_project.domain.news;

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
@Table(name = "reaction_to_news_comment")
public class ReactionToNewsComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    CommentOnNews commentId;
    @ManyToOne
    User userId;
    String reaction;
    LocalDateTime created_at;
}
