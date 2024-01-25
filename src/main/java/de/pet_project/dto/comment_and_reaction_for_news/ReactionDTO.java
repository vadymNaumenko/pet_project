package de.pet_project.dto.comment_and_reaction_for_news;

import lombok.Data;

@Data
public class ReactionDTO {
    private Long id;
    private String photoAuthor;
    private String reaction;
}
