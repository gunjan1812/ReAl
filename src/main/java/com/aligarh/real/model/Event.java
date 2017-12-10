package com.aligarh.real.model;

import com.aligarh.real.constants.Occasion;
import java.time.LocalDate;

public class Event {
    private int eventId;
    private Occasion eventType;
    private String name;
    private LocalDate eventDate;
    private String description;
    private String longDescription;
    private boolean featured;
    private boolean available;
    private boolean open;

    public Event(int eventId, Occasion eventType, String name, LocalDate eventDate, String description, String longDescription,
                 boolean featured, boolean available, boolean open) {
        this.eventId = eventId;
        this.eventType = eventType;
        this.name = name;
        this.eventDate = eventDate;
        this.description = description;
        this.longDescription = longDescription;
        this.featured = featured;
        this.available = available;
        this.open = open;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public Occasion getEventType() {
        return eventType;
    }

    public void setEventType(Occasion eventType) {
        this.eventType = eventType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}
