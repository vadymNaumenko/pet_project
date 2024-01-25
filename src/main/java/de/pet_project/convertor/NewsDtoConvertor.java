package de.pet_project.convertor;

import de.pet_project.domain.news.News;
import de.pet_project.dto.news.NewsCreateDTO;
import de.pet_project.dto.news.NewsDTO;
import de.pet_project.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewsDtoConvertor {
    private final ModelMapper modelMapper;

    public NewsDTO convertToNewsDTO(News event){

        return modelMapper.map(event, NewsDTO.class);
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
}
