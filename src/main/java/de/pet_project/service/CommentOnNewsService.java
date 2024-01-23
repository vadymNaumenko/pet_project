package de.pet_project.service;

import de.pet_project.repository.news_and_comment.CommentOnNewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentOnNewsService {

    private final CommentOnNewsRepository commentOnNewsRepository;

}
