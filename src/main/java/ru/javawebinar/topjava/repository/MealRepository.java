package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.util.Collection;

public interface MealRepository {
    Meal save(User user, Meal meal);

    void delete(User user, int id);

    Meal get(User user, int id);

    Collection<Meal> getAll(User user);
}
