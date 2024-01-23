package de.pet_project.domain.news;

import de.pet_project.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
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
    @OneToMany(mappedBy = "comment")
    private List<ReactionToNewsComment> reactions;
    LocalDateTime created_at;

}
