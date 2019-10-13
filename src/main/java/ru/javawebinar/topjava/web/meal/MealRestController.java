package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.util.List;

@Controller
public class MealRestController {
    @Autowired
    private MealService service;

    public Meal create(int userId, Meal meal) {
        return service.create(userId, meal);
    }

    public Meal get(int userId, int mealId) {
        return service.get(userId, mealId);
    }

    public void delete(int userId, int mealId) {
        service.delete(userId, mealId);
    }

    public void update(int userId, Meal meal) {
        service.update(userId, meal);
    }

    public List<Meal> getAll(int userId) {
        return service.getAll(userId);
    }
}