package de.pet_project.dto.news;

import de.pet_project.domain.news.CommentOnNews;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;

import java.time.LocalDate;
import java.util.List;

public class NewsWithCommentDTO {
    private Long id;

    private String title;
    private String imageUrl;
    private String text;
    private LocalDate date;
    private Boolean isDeleted;
    List<CommentOnNews> comment;
}
