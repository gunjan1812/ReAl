package com.aligarh.real.data;

import com.aligarh.real.constants.Occasion;
import com.aligarh.real.model.EventCategory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class EventCategoryRepository {
    private static final List<EventCategory> ALL_CATEGORIES = Arrays.asList(
            new EventCategory(1, Occasion.ADVENTURE.toString()),
            new EventCategory(2, Occasion.BUSINESS.toString()),
            new EventCategory(3, Occasion.COMPETITION.toString()),
            new EventCategory(4, Occasion.EXHIBITION.toString()),
            new EventCategory(5, Occasion.WORKSHOP.toString())
    );

    public List<EventCategory> getAllCategories() {
        return ALL_CATEGORIES;
    }

    public EventCategory findById(int id) {
        for (EventCategory category : ALL_CATEGORIES) {
            if (category.getId() == id) {
                return category;
            }
        }
        return null;
    }
}