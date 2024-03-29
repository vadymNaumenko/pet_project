package de.pet_project.convertor;

import de.pet_project.domain.news.News;
import de.pet_project.dto.news.NewsCreateDTO;
import de.pet_project.dto.news.NewsDTO;
import de.pet_project.dto.news.NewsShortCreateDTO;
import de.pet_project.repository.news_and_comment.CommentOnNewsRepository;
import de.pet_project.service.CommentOnNewsService;
import de.pet_project.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class NewsDtoConvertor {
    private final ModelMapper modelMapper;


    public NewsDTO convertToNewsDTO(News event){
        NewsDTO newsDTO = modelMapper.map(event, NewsDTO.class);
        newsDTO.setSizeComments(event.getComment().size());
        return newsDTO;
    }
    public News convertToNews(NewsDTO eventDTO){
        return modelMapper.map(eventDTO, News.class);
    }
    public News convertToNews(NewsCreateDTO eventCreateDTO){
        News news = modelMapper.map(eventCreateDTO, News.class);
        news.setIsDeleted(false);
        news.setDate(DateUtils.convertDate(eventCreateDTO.getDateTime()));
        return news;
    }

    public News convertNewsShortCreateDTOToNews(NewsShortCreateDTO createDTO) {
        News news = modelMapper.map(createDTO, News.class);
        news.setIsDeleted(false);
        news.setDate(LocalDate.now());
        return news;
    }
}
