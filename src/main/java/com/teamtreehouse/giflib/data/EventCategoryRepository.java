package com.teamtreehouse.giflib.data;

import com.teamtreehouse.giflib.constants.Occasion;
import com.teamtreehouse.giflib.model.Category;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class EventCategoryRepository {
    private static final List<Category> ALL_CATEGORIES = Arrays.asList(
            new Category(1, Occasion.ADVENTURE.toString()),
            new Category(2, Occasion.BUSINESS.toString()),
            new Category(3, Occasion.COMPETITION.toString()),
            new Category(4, Occasion.EXHIBITION.toString()),
            new Category(5, Occasion.WORKSHOP.toString())
    );

    public List<Category> getAllCategories() {
        return ALL_CATEGORIES;
    }

    public Category findById(int id) {
        for (Category category : ALL_CATEGORIES) {
            if (category.getId() == id) {
                return category;
            }
        }
        return null;
    }
}