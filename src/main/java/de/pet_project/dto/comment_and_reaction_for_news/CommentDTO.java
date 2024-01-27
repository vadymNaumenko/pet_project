package de.pet_project.dto.comment_and_reaction_for_news;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
public class CommentDTO{
    private Long id;
    private String author; //todo nickname or userDTO?
    private String text;
    private LocalDateTime created_at;;

    private List<ReactionDTO> reactions;
}
