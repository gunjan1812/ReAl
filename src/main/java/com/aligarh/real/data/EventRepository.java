package com.aligarh.real.data;

import com.aligarh.real.constants.Occasion;
import com.aligarh.real.model.Event;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class EventRepository {
    private static final List<Event> ALL_EVENTS = Arrays.asList(
            new Event(1, Occasion.ADVENTURE, "adventure-skydivin", LocalDate.of(2017, 12, 31),
                    "Tandem, Skydiving in Aligarh", "Recently, Skydiving in Aligarh gathers the attention which offers tandem jump at Aligarh airstrip on\n" +
                    "Kanpur – Aligarh expressway. In this jump, you will be connected to a harness which will be attached\n" +
                    "to a tandem instructor, who will be the lead throughout the time.",true, true, true),
            new Event(2, Occasion.BUSINESS, "business-sep29", LocalDate.of(2017, 9, 29),
                    "Self-Actualized Leadership Seminar, 30th Edition", "Who should participate and Why?<br>\n" +
                    "-Seasoned leaders wishing to become a spiritual mentor/guru.<br>\n" +
                    "-Spiritual seekers who want to realize the spiritual wisdom through a completely rational model.<br>\n" +
                    "-People seeking an end to corruption, inflation, recession, choked cities, political turmoil,\n" +
                    "communal disharmony, and last but not the least the drastic effects of Global Warming.",true, true, true),
            new Event(3, Occasion.EXHIBITION, "exhibition-oct2", LocalDate.of(2017, 10, 15),
                    "Exhibition of Books and Photographs", "Exhibition of Books and Photographs on Mahatma Gandhi is being organized on the occassion of Gandhi\n" +
                    "Jayanti and its a regular feature of the AMU. There will be a Cleaniness drive before opening the\n" +
                    "Exhibition at 9.45, followed by Gandhi Commemorative Lectures at 10am in the Cultural Hall of the MA\n" +
                    "Library",true, false, true),
            new Event(4, Occasion.WORKSHOP, "workshop-oct6", LocalDate.of(2017, 10, 15),
                    "Arduino and Embedded Systems Workshop", "A Learning by Doing Workshop on Arduino and Embedded Systems.<br>\n" +
                    "Participants with best performance will also get free projet guidance as well as internship and job\n" +
                    "opportunity from Bit N Byte laboratories pvt. ltd.\n" +
                    "\n" +
                    "<br>Registration Fee:<br>\n" +
                    "Rs 600 for SAE ZHCET members (please bring your membership acknowledgment slip during time of\n" +
                    "egistration)<br>\n" +
                    "Rs 750 for everyone else",false, false, false),
            new Event(5, Occasion.WORKSHOP, "workshop-oct6", LocalDate.of(2017, 10, 15),
                    "Arduino and Embedded Systems Workshop", "A Learning by Doing Workshop on Arduino and Embedded Systems.<br>\n" +
                    "Participants with best performance will also get free projet guidance as well as internship and job\n" +
                    "opportunity from Bit N Byte laboratories pvt. ltd.\n" +
                    "\n" +
                    "<br>Registration Fee:<br>\n" +
                    "Rs 600 for SAE ZHCET members (please bring your membership acknowledgment slip during time of\n" +
                    "egistration)<br>\n" +
                    "Rs 750 for everyone else",false, false, false),
            new Event(6, Occasion.COMPETITION, "competition-MrMiss", LocalDate.of(2017, 10, 15),
                    "Miss and Mr Aligarh", "We will choose Mr, Miss and Mrs Aligarh who will contest with other to get Uttar Pradesh crown,\n" +
                    "winner from Uttar Pradesh will represent state in India and Indian winner will represent India in\n" +
                    "world finale.",true, false, true),
            new Event(7, Occasion.EXHIBITION, "exhibition-oct3", LocalDate.of(2017, 10, 15),
                    "Laadlee Exhibition", "Karwa Chauth @ Diwali special, Life Style & Handicraft... Special. Health Special...",false, true, true),
            new Event(8, Occasion.WORKSHOP, "workshop-oct6", LocalDate.of(2017, 10, 15),
                    "Arduino and Embedded Systems Workshop", "A Learning by Doing Workshop on Arduino and Embedded Systems.<br>\n" +
                    "Participants with best performance will also get free projet guidance as well as internship and job\n" +
                    "opportunity from Bit N Byte laboratories pvt. ltd.\n" +
                    "\n" +
                    "<br>Registration Fee:<br>\n" +
                    "Rs 600 for SAE ZHCET members (please bring your membership acknowledgment slip during time of\n" +
                    "egistration)<br>\n" +
                    "Rs 750 for everyone else",false, false, false)
    );

    public Event findByName(String name) {
        for(Event event : ALL_EVENTS) {
            if(event.getName().equals(name)) {
                return event;
            }
        }
        return null;
    }

    public List<Event> getAllEvents() {
        return ALL_EVENTS;
    }

    public List<Event> findByEventCategoryId(int id) {
        List<Event> events = new ArrayList<>();
        for(Event event : ALL_EVENTS) {
            if(event.getEventId() == id) {
                events.add(event);
            }
        }
        return events;
    }
}