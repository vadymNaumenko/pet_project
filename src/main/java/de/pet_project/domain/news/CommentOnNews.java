package de.pet_project.domain.news;

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
    News postId;
    @Column(name = "comment_text")
    String text;
    @OneToMany(mappedBy = "commentId")
    private List<ReactionToNewsComment> reactions;
    LocalDateTime created_at;

}
