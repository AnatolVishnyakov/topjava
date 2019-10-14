package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

@Service
public class MealServiceImpl implements MealService {
    @Autowired
    private MealRepository repository;

    @Override
    public Meal create(int userId, Meal meal) {
        return repository.save(userId, meal);
    }

    @Override
    public void delete(int userId, int id) throws NotFoundException {
        if (repository.delete(userId, id) == null) {
            throw new NotFoundException("Not found meal id: " + id);
        }
    }

    @Override
    public Meal get(int userId, int id) throws NotFoundException {
        Meal meal = repository.get(userId, id);
        if (meal == null) {
            throw new NotFoundException("Not found meal id: " + id);
        }
        return meal;
    }

    @Override
    public void update(int userId, Meal meal) {
        repository.save(userId, meal);
    }

    @Override
    public List<Meal> getAll(int userId) {
        List<Meal> meals = repository.getAll(userId);
        if (meals.isEmpty()) {
            throw new NotFoundException("Meal for userId: " + userId + " is empty");
        }
        return meals;
    }
}