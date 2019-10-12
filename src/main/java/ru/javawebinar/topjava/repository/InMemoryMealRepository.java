package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryMealRepository implements MealRepository {
    private Map<User, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger();

    {
        MealsUtil.MEALS.forEach(meal -> save(SecurityUtil.admin(), meal));
    }

    @Override
    public Meal save(User user, Meal meal) {
        Map<Integer, Meal> meals = repository.getOrDefault(user, new HashMap<>(1));
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meals.put(meal.getId(), meal);
            repository.put(user, meals);
            return meal;
        }
//        // treat case: update, but absent in storage
        return meals.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public Meal get(User user, int id) {
        return repository.get(user)
                .getOrDefault(id, null);
    }

    @Override
    public void delete(User user, int id) {
        repository.get(user)
                .remove(id);
    }

    @Override
    public Collection<Meal> getAll(User user) {
        return repository.get(user)
                .values().stream()
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}
