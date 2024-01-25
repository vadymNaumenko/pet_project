package de.pet_project.service;

import de.pet_project.convertor.CommentDtoConvertor;
import de.pet_project.domain.User;
import de.pet_project.domain.news.CommentOnNews;
import de.pet_project.domain.news.News;
import de.pet_project.dto.comment_and_reaction_for_news.CommentDTO;
import de.pet_project.repository.news_and_comment.CommentOnNewsRepository;
import de.pet_project.repository.news_and_comment.NewsRepository;
import de.pet_project.repository.news_and_comment.ReactionToNewsCommitRepository;
import de.pet_project.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentOnNewsService {

    private final CommentOnNewsRepository commentOnNewsRepository;
    private final NewsRepository newsRepository;
    private final UserRepository userRepository;
    private final CommentDtoConvertor convertor;

    public Page<CommentDTO> findCommentByNewsId(Long newsId, Pageable pageable) {
        Page<CommentOnNews> comments = commentOnNewsRepository.findByNews_Id(newsId, pageable);
        return comments.map(convertor::convertToCommentDTO);
    }

    @Transactional
    public Optional<CommentDTO> createComment(Long newsId, String text, UserDetails userDetails) {
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
                return Optional.ofNullable(convertor.convertToCommentDTO(comment));
            } else {
                // todo news not found
            }
        } else {
            //todo mast be registration or
        }

        return Optional.empty();
    }
@Transactional
    public Optional<CommentDTO> editeComment(Long commentId, String text, UserDetails userDetails) {
        Optional<CommentOnNews> comment = commentOnNewsRepository.findById(commentId);
        Optional<User> user = userRepository.findByEmail(userDetails.getUsername());
        if (user.isPresent() && comment.isPresent()){
            if (user.get().getEmail().equals(comment.get().getUser().getEmail())){
                comment.get().setText(text);
                commentOnNewsRepository.save(comment.get());
                return Optional.ofNullable(convertor.convertToCommentDTO(comment.get()));
            } else if (user.get().getRole().equals(User.Role.ADMIN)) {
                comment.get().setText(text);
                commentOnNewsRepository.save(comment.get());
                return Optional.ofNullable(convertor.convertToCommentDTO(comment.get()));
            }else {
                //todo no right to edit comment
                return Optional.empty();
            }
        }
        return Optional.empty();
    }
}
