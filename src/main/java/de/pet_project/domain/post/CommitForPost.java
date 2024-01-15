package de.pet_project.domain.post;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "commit_for_post")
public class CommitForPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    Event postId;
    @Column(name = "comment_text")
    String text;
    LocalDateTime created_at;

//    CREATE TABLE commit_for_post
//            (
//                    id BIGSERIAL PRIMARY KEY,
//                    post_id BIGINT REFERENCES events (id),
//    user_id BIGINT REFERENCES users (user_id),
//    comment_text text,
//    create_at timestamp
//);
//
//--changeset vadym:2
//    CREATE TABLE reaction_to_post_commit
//            (
//                    id BIGSERIAL PRIMARY KEY,
//                    commit_id BIGINT REFERENCES commit_for_post (id),
//    user_id BIGINT REFERENCES users (user_id),
//    reaction varchar(10),
//    create_at timestamp
//);
}
