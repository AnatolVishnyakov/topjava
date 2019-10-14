package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<MealWithExceed> getAll(int userId) {
        List<Meal> meals = service.getAll(userId);
        return meals.stream()
                .map(meal -> MealsUtil.createWithExceed(meal, true))
                .collect(Collectors.toList());
    }
}