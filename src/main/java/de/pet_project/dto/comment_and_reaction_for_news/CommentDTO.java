package de.pet_project.dto.comment_and_reaction_for_news;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
@Data
@Builder
public class CommentDTO{
    private Long id;
    private String author; //todo nickname or userDTO?
    private String text;
    private LocalDateTime localDateTime;

    private List<ReactionDTO> reactions;
}
