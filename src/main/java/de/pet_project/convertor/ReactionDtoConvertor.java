package de.pet_project.convertor;

import de.pet_project.domain.news.ReactionToNewsComment;
import de.pet_project.dto.comment_and_reaction_for_news.ReactionDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReactionDtoConvertor {
    private final ModelMapper modelMapper;

    public ReactionDTO convertToReactionDTO(ReactionToNewsComment reaction) {
        ReactionDTO dto = modelMapper.map(reaction, ReactionDTO.class);
        dto.setPhotoAuthor(reaction.getUser().getAvatar());
        return dto;
    }
}
