package de.pet_project.utils;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@Slf4j
public class DateUtils {

    private final static String[] MONTHS_RU = {"января", "февраля", "марта", "апреля", "мая", "июня", "июля", "августа",
            "сентября", "октября", "ноября", "декабря"};
    private final static String[] MONTHS_DE = {"januar", "februar", "märz", "april", "mai", "juni", "juli", "august",
            "september", "oktober", "november", "dezember"};

    private final static String[] MONTHS_UA = {"січня", "лютого", "березня", "квітня", "травня", "червня", "липня", "серпня",
            "вересня", "жовтня", "листопада", "грудня"};
    private final static String[] MONTHS_EN = {"january", "february", "march", "april", "may", "june", "july", "august",
            "september", "october", "november", "december"};


    /**
     * Convert date-time in String to LocalDateTime
     *
     * @param strDate
     * @return LocalDate
     */
    public static LocalDate convertDate(String strDate) {
        LocalDate dateTime = null;
        if(strDate.equals("Today")){
            return LocalDate.now();
        }
        String[] dt = strDate.split("[ ,\\.]+");
        if (dt.length == 3) {
            dt = new String[]{dt[0], dt[1], dt[2]};
        }

        int idx = Arrays.asList(MONTHS_EN).indexOf(dt[1].toLowerCase());
        if (idx < 0) {
            idx = Arrays.asList(MONTHS_DE).indexOf(dt[1].toLowerCase());
            if (idx < 0) {
                idx = Arrays.asList(MONTHS_UA).indexOf(dt[1].toLowerCase());
                if (idx < 0) {
                    idx = Arrays.asList(MONTHS_RU).indexOf(dt[1].toLowerCase());
                }
            }
        }
        dt[1] = String.valueOf(idx + 1);

        try {
            dateTime = LocalDate.parse(String.join(" ", dt), DateTimeFormatter.ofPattern("d M yyyy"));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return dateTime;
    }


    public static void main(String[] args) {

        String dt = "17. february, 2023";
        System.out.println(convertDate(dt));

    }
}
