package de.pet_project.convertor;

import de.pet_project.domain.news.CommentOnNews;
import de.pet_project.domain.news.ReactionToNewsComment;
import de.pet_project.dto.comment_and_reaction_for_news.CommentDTO;
import de.pet_project.dto.comment_and_reaction_for_news.ReactionDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CommentDtoConvertor {
    private final ModelMapper modelMapper;

    public CommentDTO convertToCommentDTO(CommentOnNews comment){
        List<ReactionDTO> reactionDTOList = comment.getReactions().stream()
                .map(this::convertToReactionDTO).toList();
        CommentDTO commentDTO = modelMapper.map(comment,CommentDTO.class);
        commentDTO.setAuthor(comment.getUser().getNickname()); //todo nickname
        commentDTO.setReactions(reactionDTOList);
        return commentDTO;
    }
    public CommentDTO convertToNewCommentDTO(CommentOnNews comment){
        CommentDTO commentDTO = modelMapper.map(comment,CommentDTO.class);
        commentDTO.setAuthor(comment.getUser().getNickname()); //todo nickname

        return commentDTO;
    }
    private ReactionDTO convertToReactionDTO(ReactionToNewsComment reaction){
        ReactionDTO reactionDTO = modelMapper.map(reaction,ReactionDTO.class);
        reactionDTO.setPhotoAuthor(reaction.getUser().getAvatar());
        return reactionDTO;
    }

}
