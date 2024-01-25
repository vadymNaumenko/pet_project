package de.pet_project.domain.news;

import de.pet_project.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comment_on_news")
public class CommentOnNews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "news_id")
    private News news;
    @Column(name = "comment_text")
    private String text;
    private boolean isDeleted;
    @OneToMany(mappedBy = "comment")
    private List<ReactionToNewsComment> reactions;
    LocalDateTime created_at;

}
