package de.pet_project.dto.comment_and_reaction_for_news;

import java.time.LocalDateTime;
import java.util.List;

public class CommentDTO{
    private String author; //todo nickname or userDTO?
    private String text;
    private LocalDateTime localDateTime;

    private List<ReactionDTO> reactions;
}
