package de.pet_project.controller;

import de.pet_project.dto.comment_and_reaction_for_news.CommentDTO;
import de.pet_project.service.CommentOnNewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/comments")
@Slf4j
public class CommentOnNewsController {

    private final CommentOnNewsService commentOnNewsService;

    @GetMapping("/{newsId}")
    public Page<CommentDTO> findCommentByNewsId(@PathVariable Long newsId, Pageable pageable) {
        log.info("Fetching comments for news with ID: {}", newsId);
        return commentOnNewsService.findCommentByNewsId(newsId, pageable);
    }

    @GetMapping("/authorAvatar/{commentId}")
    public ResponseEntity<byte[]> getAuthorAvatar(@PathVariable Long commentId) {
        log.info("Fetching author avatar for comment with ID: {}", commentId);
        return commentOnNewsService.findUserAvatarByCommentId(commentId)
                .map(content -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                        .contentLength(content.length)
                        .body(content))
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @GetMapping("/all/{newsId}")
    public List<CommentDTO> findCommentByNewsId(@PathVariable Long newsId) {
        log.info("Fetching all comments for news with ID: {}", newsId);
        return commentOnNewsService.findCommentByNewsId(newsId);
    }

    @PostMapping
    public ResponseEntity<CommentDTO> createComment(@RequestParam("news-id") Long newsId, String text, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("Creating comment for news with ID: {} by user: {}", newsId, userDetails.getUsername());
        Optional<CommentDTO> commentDTO = commentOnNewsService.createComment(newsId, text, userDetails);
        return commentDTO.map(dto -> ResponseEntity.status(HttpStatus.OK).body(dto))
                .orElseGet(() -> ResponseEntity.badRequest().body(null));
    }

    @PutMapping
    public ResponseEntity<CommentDTO> editComment(@RequestParam("id") Long id, String text, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("Editing comment with ID: {} by user: {}", id, userDetails.getUsername());
        if (userDetails == null)
            return null; // todo du must registration
        Optional<CommentDTO> commentDTO = commentOnNewsService.editComment(id, text, userDetails);
        return commentDTO.map(dto -> ResponseEntity.status(HttpStatus.OK).body(dto))
                .orElseGet(() -> ResponseEntity.badRequest().body(null));
    }

    @DeleteMapping
    public HttpStatus deleteComment(@RequestParam("id") Long commentId, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("Deleting comment with ID: {} by user: {}", commentId, userDetails.getUsername());
        if (commentOnNewsService.deleteById(commentId, userDetails)) {
            return HttpStatus.OK;
        }
        return HttpStatus.BAD_REQUEST;
    }
}
