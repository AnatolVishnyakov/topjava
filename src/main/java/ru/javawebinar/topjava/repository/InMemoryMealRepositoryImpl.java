package ru.javawebinar.topjava.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.Util;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private static final Logger logger = LoggerFactory.getLogger(InMemoryMealRepositoryImpl.class);
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger();

    {
        MealsUtil.MEALS.forEach(meal -> save(SecurityUtil.authUserId(), meal));
    }

    @PostConstruct
    public void postConstruct() {
        logger.info("+++ PostConstruct");
    }

    @PreDestroy
    public void preDestroy() {
        logger.info("+++ PreDestroy");
    }

    @Override
    public Meal save(int userId, Meal meal) {
        Map<Integer, Meal> meals = repository.getOrDefault(userId, new ConcurrentHashMap<>());
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meals.put(meal.getId(), meal);
            repository.put(userId, meals);
            return meal;
        }
//        // treat case: update, but absent in storage
        return meals.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public Meal get(int id, int userId) {
        return repository
                .getOrDefault(userId, Collections.emptyMap())
                .getOrDefault(id, null);
    }

    @Override
    public boolean delete(int id, int userId) {
        Map<Integer, Meal> meals = repository.get(userId);
        return meals != null &&
                meals.remove(id) != null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        Map<Integer, Meal> meals = repository.get(userId);
        return meals == null || meals.isEmpty()
                ? Collections.emptyList()
                : meals.values().stream()
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return getAllFiltered(userId, meal -> Util.isBetween(meal.getDateTime(), startDateTime, endDateTime));
    }

    private List<Meal> getAllFiltered(int userId, Predicate<Meal> filter) {
        Map<Integer, Meal> meals = repository.get(userId);
        return CollectionUtils.isEmpty(meals) ? Collections.emptyList() :
                meals.values().stream()
                        .filter(filter)
                        .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                        .collect(Collectors.toList());
    }
}
