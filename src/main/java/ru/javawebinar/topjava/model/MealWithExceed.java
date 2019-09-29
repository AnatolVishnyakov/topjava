package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

// Еда с превышением калорий
public class MealWithExceed {
    private static AtomicInteger counter = new AtomicInteger(0);
    protected final int id;
    protected final LocalDateTime dateTime;
    protected final String description;
    protected final int calories;
    protected final boolean exceed; // превышение калорий

    public MealWithExceed(Meal meal, boolean exceed) {
        this.dateTime = meal.dateTime;
        this.description = meal.description;
        this.calories = meal.calories;
        this.exceed = exceed;
        this.id = counter.incrementAndGet();
    }

    public MealWithExceed(LocalDateTime dateTime, String description, int calories, boolean exceed) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.exceed = exceed;
        this.id = counter.incrementAndGet();
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
                "dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", exceed=" + exceed +
                '}';
    }
}