package de.pet_project.service;

import de.pet_project.convertor.EventDtoConvertor;
import de.pet_project.domain.Event;
import de.pet_project.dto.event.EventCreateDTO;
import de.pet_project.dto.event.EventDTO;
import de.pet_project.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
                events.add(new EventCreateDTO(title, image, arr[0], arr[1], date));
            }

        } catch (IOException e) {
            log.error(e.getMessage());
        }

        for (EventCreateDTO dto : events) {
            Event event = eventDtoConvertor.convertToEvent(dto);
            eventRepository.save(event);
        }

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

    public Page<EventDTO> findAll(Pageable pageable) {
        return eventRepository.findAll(pageable)
                .map(eventDtoConvertor::convertToEventDTO);
    }

    public Optional<EventDTO> findById(Long id) {
        return eventRepository.findById(id)
                .map(eventDtoConvertor::convertToEventDTO);
    }
    @Transactional
    public Optional<EventDTO> update(EventDTO dto)  {
        if (eventRepository.existsById(dto.getId()))
            eventRepository.save(eventDtoConvertor.convertToEvent(dto));

        return eventRepository.findById(dto.getId())
                .map(eventDtoConvertor::convertToEventDTO);

    }

    public List<EventDTO> findAllByTitle(String str) {
        return eventRepository.findAllByTitle(str)
                .stream()
                .map(eventDtoConvertor::convertToEventDTO)
                .collect(Collectors.toList());
    }

    public List<EventDTO> findByTitle(String str) {
        return eventRepository.findByTitle(str)
                .stream()
                .map(eventDtoConvertor::convertToEventDTO)
                .collect(Collectors.toList());
    }

    @Transactional
//    @PreAuthorize("hasRole('ADMIN')")
    public Optional<EventDTO> delete(Long id) {
        return eventRepository.findById(id).map(event -> {
            event.setIsDeleted(true);
          eventRepository.save(event);
          return eventDtoConvertor.convertToEventDTO(event);
        });

    }

    public boolean hasEvent(String url) {
        return eventRepository.existsByImageUrl(url);
    }
}

