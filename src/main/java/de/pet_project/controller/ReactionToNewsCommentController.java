package de.pet_project.controller;

import de.pet_project.dto.comment_and_reaction_for_news.ReactionDTO;
import de.pet_project.service.ReactionToNewsCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reactionToNewsComment")
@Slf4j
public class ReactionToNewsCommentController {

    private final ReactionToNewsCommentService reactionService;

    @PostMapping
    public ReactionDTO setReaction(@RequestParam("commentId") Long commentId,
                                   @RequestParam String reaction,
                                   @AuthenticationPrincipal UserDetails userDetails) {
        log.info("Setting reaction to comment with ID: {} by user: {}", commentId, userDetails.getUsername());
        ReactionDTO result = reactionService.setReactionToComment(commentId, reaction, userDetails);
        log.info("Reaction set successfully");
        return result;
    }

    @DeleteMapping
    public HttpStatus deleteReaction(@RequestParam Long reactionId,
                                     @AuthenticationPrincipal UserDetails userDetails) {
        log.info("Deleting reaction with ID: {} by user: {}", reactionId, userDetails.getUsername());
        if (reactionService.deleteReaction(reactionId, userDetails)) {
            log.info("Reaction deleted successfully");
            return HttpStatus.OK;
        }
        log.warn("Failed to delete reaction with ID: {}", reactionId);
        return HttpStatus.BAD_REQUEST;
    }
}
