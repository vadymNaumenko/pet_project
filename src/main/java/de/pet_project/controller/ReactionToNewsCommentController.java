package de.pet_project.controller;

import de.pet_project.dto.comment_and_reaction_for_news.ReactionDTO;
import de.pet_project.service.ReactionToNewsCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reactionToNewsComment")
public class ReactionToNewsCommentController {

    private final ReactionToNewsCommentService reactionService;
    @PostMapping
    public ReactionDTO setReaction(@RequestParam("commentId") Long commentId,
                                   @RequestParam String reaction,
                                   @AuthenticationPrincipal UserDetails userDetails){
       return reactionService.setReactionToComment(commentId,reaction,userDetails);
    }

    @DeleteMapping
    public HttpStatus deleteReaction(@RequestParam Long reactionId,
                                     @AuthenticationPrincipal UserDetails userDetails){
      if (reactionService.deleteReaction(reactionId,userDetails)){
          return HttpStatus.OK;
      }
      return HttpStatus.BAD_REQUEST;
    }

}
