package de.pet_project.service;

import de.pet_project.domain.Event;
import de.pet_project.dto.event.EventCreateDTO;
import de.pet_project.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventService {

    private final EventRepository eventRepository;
    private boolean enable = true; // todo mast be add in application.yml
    private String sitUrl = "https://www.uploadvr.com/reviews";
//    DEFAULT  'https://www.uploadvr.com'


    //    private List<EventCreateDTO> readTitle(){
    @Scheduled(fixedRate = 2 * 60 * 1000)
    private void readTitle() {
        ArrayList<EventCreateDTO> events = new ArrayList<>();

        try {
            Document document = Jsoup.connect(sitUrl).get();
            Elements elements = document.select(".js-card");

            for (Element element : elements) {
                String image = "https://www.uploadvr.com" + element.select(".c-card__image").attr("data-src");//data-src
                String title = element.select(".c-card__headline").text();
                String date = element.select(".c-timestamp").text();
                String urlToNews = "https://www.uploadvr.com" + element.select(".c-card__headline a").attr("href");

                events.add(new EventCreateDTO(title, image, urlToNews, date));
            }

        } catch (IOException e) {
            log.error(e.getMessage());
        }

//        return events;
        System.out.println(events);
    }

}
