package de.pet_project.service;

import de.pet_project.convertor.ReactionDtoConvertor;
import de.pet_project.domain.news.CommentOnNews;
import de.pet_project.domain.news.ReactionToNewsComment;
import de.pet_project.dto.comment_and_reaction_for_news.ReactionDTO;
import de.pet_project.repository.news_and_comment.CommentOnNewsRepository;
import de.pet_project.repository.news_and_comment.ReactionToNewsCommitRepository;
import de.pet_project.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReactionToNewsCommentService {

    private final ReactionToNewsCommitRepository reactionRepository;
    private final CommentOnNewsRepository commentOnNewsRepository;
    private final ReactionDtoConvertor convertor;
    private final UserRepository userRepository;

    @Transactional
    public ReactionDTO setReactionToComment(Long commentId, String reaction, UserDetails userDetails) {
        Optional<CommentOnNews> comment = commentOnNewsRepository.findById(commentId);
        ReactionToNewsComment reactionToNewsComment = new ReactionToNewsComment();
        if (comment.isPresent()) {
            reactionToNewsComment.setComment(comment.get());
            reactionToNewsComment.setReaction(reaction);//todo if reaction not null
            reactionToNewsComment.setCreated_at(LocalDateTime.now());
            if (userDetails != null) {
                reactionToNewsComment.setUser(userRepository.findByEmail(userDetails
                        .getUsername()).orElseThrow(() -> new UsernameNotFoundException("user not found")));
            } else {
                throw new UsernameNotFoundException("userDetails is empty");
            }
        }
       return convertor.convertToReactionDTO(reactionRepository.save(reactionToNewsComment));

    }

    public boolean deleteReaction(Long reactionId, UserDetails userDetails) {
        Optional<ReactionToNewsComment> reaction = reactionRepository.findById(reactionId);
        if (userDetails != null && reaction.isPresent()){
            if (reaction.get().getUser().getEmail().equals(userDetails.getUsername())){
                reactionRepository.deleteById(reactionId);
                return true;
            }
        }
        return false;
    }
}
