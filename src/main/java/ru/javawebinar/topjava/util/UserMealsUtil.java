package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> userMeals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        System.out.println("Implements video");
        List<UserMealWithExceed> mealsWithExceeded = getFilteredWithExceeded(userMeals, LocalTime.of(7, 0), LocalTime.of(12, 0), 1000);
        mealsWithExceeded.forEach(System.out::println);

        System.out.println("My implements");
        mealsWithExceeded = getFilteredMealsWithExceeded(userMeals, LocalTime.of(7, 0), LocalTime.of(12, 0), 1000);
        mealsWithExceeded.forEach(System.out::println);

        System.out.println("Cycle implements");
        mealsWithExceeded = getFilteredMealsWithExceededByCycle(userMeals, LocalTime.of(7, 0), LocalTime.of(12, 0), 1000);
        mealsWithExceeded.forEach(System.out::println);

        System.out.println("Implements O(N) cycle");
        mealsWithExceeded = getFilteredWithExceededByOneCycle(userMeals, LocalTime.of(7, 0), LocalTime.of(12, 0), 1000);
        mealsWithExceeded.forEach(System.out::println);

        System.out.println("Implements O(N) Stream API");
        mealsWithExceeded = getFilteredWithExceededInOneReturn(userMeals, LocalTime.of(7, 0), LocalTime.of(12, 0), 1000);
        mealsWithExceeded.forEach(System.out::println);
    }

    // Реализация фильтра через Stream API
    public static List<UserMealWithExceed> getFilteredMealsWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        return mealList.stream()
                .filter(userMeal -> {
                    LocalTime currentTime = userMeal.getDateTime().toLocalTime();
                    return currentTime.isAfter(startTime) && currentTime.isBefore(endTime);
                })
                .map(userMeal -> new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), (userMeal.getCalories() > caloriesPerDay)))
                .collect(Collectors.toList());
    }

    // Реализация фильтра через циклы
    public static List<UserMealWithExceed> getFilteredMealsWithExceededByCycle(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumPerDate = new HashMap<>();
        for (UserMeal meal : mealList) {
            LocalDate mealDate = meal.getDateTime().toLocalDate();
            caloriesSumPerDate.put(mealDate, caloriesSumPerDate.getOrDefault(mealDate, 0) + meal.getCalories());
        }

        List<UserMealWithExceed> mealExceeded = new ArrayList<>();
        for (UserMeal meal : mealList) {
            LocalDateTime dateTime = meal.getDateTime();
            if (TimeUtil.isBetween(dateTime.toLocalTime(), startTime, endTime)) {
                mealExceeded.add(new UserMealWithExceed(dateTime, meal.getDescription(), meal.getCalories(), caloriesSumPerDate.get(meal.getDate()) > caloriesPerDay));
            }
        }

        return mealExceeded;
    }

    // Реализация через Stream API (видео)
    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> userMeals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = userMeals.stream()
                .collect(
                        Collectors.groupingBy(UserMeal::getDate, Collectors.summingInt(UserMeal::getCalories))
//                      Collectors.toMap(UserMeal::getDate, UserMeal::getCalories, Integer::sum)
                );

        return userMeals.stream()
                .filter(meal -> TimeUtil.isBetween(meal.getTime(), startTime, endTime))
                .map(meal ->
                        new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(),
                                caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static List<UserMealWithExceed> getFilteredWithExceededByOneCycle(List<UserMeal> userMeals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        final Map<LocalDate, Integer> caloriesSumByDate = new HashMap<>(); // группировка калорий по дням
        final Map<LocalDate, AtomicBoolean> exceededMap = new HashMap<>();

        final List<UserMealWithExceed> mealWithExceeds = new ArrayList<>();
        userMeals.forEach(meal -> {
            AtomicBoolean wrapBoolean = exceededMap.computeIfAbsent(meal.getDate(), date -> new AtomicBoolean());
            Integer dailyCalories = caloriesSumByDate.merge(meal.getDate(), meal.getCalories(), Integer::sum);
            if (dailyCalories > caloriesPerDay) {
                wrapBoolean.set(true);
            }
            if (TimeUtil.isBetween(meal.getTime(), startTime, endTime)) {
                mealWithExceeds.add(new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), wrapBoolean.get()));
            }
        });

        return mealWithExceeds;
    }

    public static List<UserMealWithExceed> getFilteredWithExceededInOneReturn(List<UserMeal> userMeals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // Сгруппированные по дате UserMeal
        Collection<List<UserMeal>> list = userMeals.stream()
                .collect(Collectors.groupingBy(UserMeal::getDate)).values();

        return list.stream().flatMap(dayMeals -> {
            boolean exceeded = dayMeals.stream().mapToInt(UserMeal::getCalories).sum() > caloriesPerDay; // суммирование сгруппированных по дням калорий
            return dayMeals.stream().filter(meal ->
                    TimeUtil.isBetween(meal.getTime(), startTime, endTime))
                    .map(meal -> new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), exceeded));
        }).collect(Collectors.toList());
    }
}