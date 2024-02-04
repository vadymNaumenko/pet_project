package de.pet_project.service;

import de.pet_project.convertor.CommentDtoConvertor;
import de.pet_project.domain.User;
import de.pet_project.domain.news.CommentOnNews;
import de.pet_project.domain.news.News;
import de.pet_project.dto.comment_and_reaction_for_news.CommentDTO;
import de.pet_project.repository.news_and_comment.CommentOnNewsRepository;
import de.pet_project.repository.news_and_comment.NewsRepository;
import de.pet_project.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentOnNewsService {

    private final CommentOnNewsRepository commentOnNewsRepository;
    private final NewsRepository newsRepository;
    private final UserRepository userRepository;
    private final CommentDtoConvertor convertor;
    private final ImageService imageService;

    public Page<CommentDTO> findCommentByNewsId(Long newsId, Pageable pageable) {
        log.info("Fetching comments for news with ID: {}", newsId);
        Page<CommentOnNews> comments = commentOnNewsRepository.findByNews_Id(newsId, pageable);
        return comments.map(convertor::convertToCommentDTO);
    }

    public List<CommentDTO> findCommentByNewsId(Long newsId) {
        log.info("Fetching comments for news with ID: {}", newsId);
        List<CommentOnNews> comments = commentOnNewsRepository.findByNews_Id(newsId);
        return comments.stream().map(convertor::convertToCommentDTO).toList();
    }

    @Transactional
    public Optional<CommentDTO> createComment(Long newsId, String text, UserDetails userDetails) {
        log.info("Creating a new comment for news with ID: {} by user: {}", newsId, userDetails.getUsername());
        Optional<User> user = userRepository.findByEmail(userDetails.getUsername());
        Optional<News> news = newsRepository.findById(newsId);
        CommentOnNews commentOnNews = new CommentOnNews();
        if (user.isPresent()) {
            commentOnNews.setUser(user.get());
            if (news.isPresent()) {
                commentOnNews.setNews(news.get());
                commentOnNews.setText(text);
                commentOnNews.setCreated_at(LocalDateTime.now());
                CommentOnNews comment = commentOnNewsRepository.save(commentOnNews);
                CommentDTO commentDTO = new CommentDTO();
                commentDTO.setAuthor("user: " + user.get().getNickname()); //todo nickname
                commentDTO.setCreated_at(comment.getCreated_at());
                commentDTO.setText(comment.getText());
                commentDTO.setId(comment.getId());
                return Optional.of(commentDTO);
            } else {
                log.error("News not found with ID: {}", newsId);
            }
        } else {
            log.error("User not found with email: {}", userDetails.getUsername());
        }

        return Optional.empty();
    }

    @Transactional
    public Optional<CommentDTO> editComment(Long commentId, String text, UserDetails userDetails) {
        log.info("Editing comment with ID: {} by user: {}", commentId, userDetails.getUsername());
        Optional<CommentOnNews> comment = commentOnNewsRepository.findById(commentId);
        Optional<User> user = userRepository.findByEmail(userDetails.getUsername());
        if (user.isPresent() && comment.isPresent()) {
            if (user.get().getEmail().equals(comment.get().getUser().getEmail())) {
                comment.get().setText(text);
                commentOnNewsRepository.save(comment.get());
                return Optional.ofNullable(convertor.convertToCommentDTO(comment.get()));
            } else if (user.get().getRole().equals(User.Role.ADMIN)) {
                comment.get().setText(text);
                commentOnNewsRepository.save(comment.get());
                return Optional.ofNullable(convertor.convertToCommentDTO(comment.get()));
            } else {
                log.error("User: {} has no right to edit comment with ID: {}", userDetails.getUsername(), commentId);
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    @Transactional
    public boolean deleteById(Long commentId, UserDetails userDetails) {
        log.info("Deleting comment with ID: {} by user: {}", commentId, userDetails.getUsername());
        Optional<CommentOnNews> comment = commentOnNewsRepository.findById(commentId);
        Optional<User> user = userRepository.findByEmail(userDetails.getUsername());
        if (user.isPresent() && comment.isPresent()) {
            if (user.get().getEmail().equals(comment.get().getUser().getEmail())) {
                comment.get().setDeleted(true);
                commentOnNewsRepository.save(comment.get());
                return true;
            } else if (user.get().getRole().equals(User.Role.ADMIN)) {
                comment.get().setDeleted(true);
                commentOnNewsRepository.save(comment.get());
                return true;
            } else {
                log.error("User: {} has no right to delete comment with ID: {}", userDetails.getUsername(), commentId);
                return false;
            }
        }
        return false;
    }

    public Optional<byte[]> findUserAvatarByCommentId(Long commentId) {
        log.info("Fetching user avatar for comment with ID: {}", commentId);
        Optional<CommentOnNews> comment = commentOnNewsRepository.findById(commentId);
        return comment
                .map(commentOnNews -> commentOnNews.getUser().getAvatar())
                .filter(StringUtils::hasText)
                .flatMap(imageService::get);
    }
}
