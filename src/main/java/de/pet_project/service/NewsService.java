package de.pet_project.service;

import de.pet_project.convertor.NewsDtoConvertor;
import de.pet_project.domain.news.News;
import de.pet_project.dto.news.NewsCreateDTO;
import de.pet_project.dto.news.NewsDTO;
import de.pet_project.dto.news.NewsShortCreateDTO;
import de.pet_project.repository.news_and_comment.CommentOnNewsRepository;
import de.pet_project.repository.news_and_comment.NewsRepository;
import de.pet_project.repository.news_and_comment.ReactionToNewsCommitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsService {

    private final NewsRepository newsRepository;
    private final NewsDtoConvertor newsDtoConvertor;
    private boolean enable = true; // todo mast be add in application.yml
    private String newsPage = "https://www.uploadvr.com/reviews";
    private String site = "https://www.uploadvr.com";


    @Scheduled(fixedRate = 2 * 60 * 60 * 1000)
    void readNews() {
        ArrayList<NewsCreateDTO> events = new ArrayList<>();
        try {
            Document document = Jsoup.connect(newsPage).get();
            Elements elements = document.select(".js-card");

            for (Element element : elements) {
                String image = "https://www.uploadvr.com" + element.select(".c-card__image").attr("data-src");//data-src

                if (checkNews(image)) { // todo add check news
                    continue;
                }

                String title = element.select(".c-card__headline").text();
                String date = element.select(".c-timestamp").text();
                String urlToNews = "https://www.uploadvr.com" + element.select(".c-card__headline a").attr("href");
                String[] arr = readText(urlToNews);
                events.add(new NewsCreateDTO(title, image, arr[0], arr[1], date));
            }

        } catch (IOException e) {
            log.error(e.getMessage());
        }

        for (NewsCreateDTO dto : events) {
            News news = newsDtoConvertor.convertToNews(dto);
            newsRepository.save(news);
        }

    }


    private String[] readText(String urlToNews) throws IOException {
        String[] str = new String[2];
        Document document = Jsoup.connect(urlToNews).get();
        String video = document.select(".fluid-width-video-wrapper iframe").attr("src");
//        Elements elements = document.select(".c-content p");
        Elements elements = document.select(".c-content");
//        String p = elements.html();
        String p = elements.toString();

        str[0] = video; // todo mast be make refactoring
        str[1] = p;
        return str;
    }


    private boolean checkNews(String imageUrl) {
        return newsRepository.existsByImageUrl(imageUrl);
    }

    public Page<NewsDTO> findAll(Pageable pageable) {
        return newsRepository.findAll(pageable)
                .map(newsDtoConvertor::convertToNewsDTO);
    }

    public Optional<NewsDTO> findById(Long id) {
        return newsRepository.findById(id)
                .map(newsDtoConvertor::convertToNewsDTO);
    }

    @Transactional
    public Optional<NewsDTO> update(NewsDTO dto) {
        if (newsRepository.existsById(dto.getId()))
            newsRepository.save(newsDtoConvertor.convertToNews(dto));

        return newsRepository.findById(dto.getId())
                .map(newsDtoConvertor::convertToNewsDTO);

    }

    public List<NewsDTO> findAllByTitle(String str) {
        return newsRepository.findAllByTitle(str)
                .stream()
                .map(newsDtoConvertor::convertToNewsDTO)
                .collect(Collectors.toList());
    }

    public List<NewsDTO> findByTitle(String str) {
        return newsRepository.findByTitle(str)
                .stream()
                .map(newsDtoConvertor::convertToNewsDTO)
                .collect(Collectors.toList());
    }

    @Transactional
//    @PreAuthorize("hasRole('ADMIN')")
    public Optional<NewsDTO> delete(Long id) {
        return newsRepository.findById(id).map(event -> {
            event.setIsDeleted(true);
            newsRepository.save(event);
            return newsDtoConvertor.convertToNewsDTO(event);
        });

    }

    public boolean hasEvent(String url) {
        return newsRepository.existsByImageUrl(url);
    }

    @Transactional
    public Optional<NewsDTO> createNews(NewsShortCreateDTO createDTO) {
        News news = newsRepository.save(newsDtoConvertor.convertNewsShortCreateDTOToNews(createDTO));
        return Optional.ofNullable(newsDtoConvertor.convertToNewsDTO(news));
    }
}

