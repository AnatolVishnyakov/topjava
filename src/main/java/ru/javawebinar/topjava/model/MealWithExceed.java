package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

// Еда с превышением калорий
public class MealWithExceed {
    private static AtomicInteger counter = new AtomicInteger();
    private final int id = counter.incrementAndGet();
    private final LocalDateTime dateTime;
    private final String description;
    private final int calories;
    private final boolean exceed; // превышение калорий

    public MealWithExceed(Meal meal, boolean exceed) {
        this.dateTime = meal.dateTime;
        this.description = meal.description;
        this.calories = meal.calories;
        this.exceed = exceed;
    }

    public MealWithExceed(LocalDateTime dateTime, String description, int calories, boolean exceed) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.exceed = exceed;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public boolean isExceed() {
        return exceed;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "MealWithExceed{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", exceed=" + exceed +
                '}';
    }
}