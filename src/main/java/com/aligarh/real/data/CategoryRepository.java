package com.aligarh.real.data;

import com.aligarh.real.model.EventCategory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class CategoryRepository {
    private static final List<EventCategory> ALL_CATEGORIES = Arrays.asList(
        new EventCategory(1, "Technology"),
        new EventCategory(2, "People"),
        new EventCategory(3, "Destruction")
    );

    public List<EventCategory> getAllCategories() {
        return ALL_CATEGORIES;
    }

    public EventCategory findById(int id) {
        for(EventCategory category : ALL_CATEGORIES) {
            if(category.getId() == id) {
                return category;
            }
        }
        return null;
    }
}