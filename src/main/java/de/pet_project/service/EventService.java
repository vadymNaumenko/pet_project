package de.pet_project.service;

import de.pet_project.convertor.EventDtoConvertor;
import de.pet_project.domain.Event;
import de.pet_project.dto.event.EventCreateDTO;
import de.pet_project.dto.event.EventDTO;
import de.pet_project.repository.EventRepository;
import de.pet_project.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventService {

    private final EventRepository eventRepository;
    private final EventDtoConvertor eventDtoConvertor;
    private boolean enable = true; // todo mast be add in application.yml
    private String newsPage = "https://www.uploadvr.com/reviews";
    private String site = "https://www.uploadvr.com";


//    @Scheduled(fixedRate = 2 * 60 * 1000)
    private void readNews() {
        ArrayList<EventCreateDTO> events = new ArrayList<>();
        try {
            Document document = Jsoup.connect(newsPage).get();
            Elements elements = document.select(".js-card");

            for (Element element : elements) {
                String image = "https://www.uploadvr.com" + element.select(".c-card__image").attr("data-src");//data-src

//                if (checkNews(image)){ // todo add check news
//                    continue;
//                }

                String title = element.select(".c-card__headline").text();
                String date = element.select(".c-timestamp").text();
                String urlToNews = "https://www.uploadvr.com" + element.select(".c-card__headline a").attr("href");
                String[] arr = readText(urlToNews);
                events.add(new EventCreateDTO(title, image,arr[0], arr[1], date));
            }

        } catch (IOException e) {
            log.error(e.getMessage());
        }

        for(EventCreateDTO dto:events ){
            Event event = new Event();
            event.setTitle(dto.getTitle());
            event.setText(dto.getText());
            event.setImageUrl(dto.getImageUrl());
            event.setDate(DateUtils.convertDate(dto.getDateTime()));
            eventRepository.save(event);
        }

//        return events;
        System.out.println();
    }


    private String[] readText(String urlToNews) throws IOException {
        String[] str = new String[2];
        Document document = Jsoup.connect(urlToNews).get();
        String video = document.select(".fluid-width-video-wrapper iframe").attr("src");
        Elements elements = document.select(".c-content p");
//        String p = elements.html();
        String p = elements.toString();

        str[0] = video; // todo mast be make refactoring
        str[1] = p;
        return str;
    }


    private boolean checkNews(String imageUrl) {
        return eventRepository.existsByImageUrl(imageUrl);
    }

    public Page<Event> findAll(Pageable pageable) {
       return eventRepository.findAll(pageable);
    }

    public Optional<EventDTO> findById(Long id) {
        return eventRepository.findById(id)
                .map(eventDtoConvertor::convertToEventDTO);
    }

}
