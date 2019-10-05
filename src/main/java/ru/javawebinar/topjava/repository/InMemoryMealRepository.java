package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.HashMap;
import java.util.Map;

public class InMemoryMealRepository implements MealRepository {
    private Map<Integer, Meal> repository = new HashMap<>();

    @Override
    public void create(Meal meal) {
        repository.put(meal.getId(), meal);
    }

    @Override
    public Meal get(int id) {
        return repository.get(id);
    }

    @Override
    public void update(Meal meal) {
        if (repository.containsKey(meal.getId())) {
            repository.put(meal.getId(), meal);
        }
    }

    @Override
    public void delete(Meal meal) {
        repository.remove(meal.getId());
    }
}
