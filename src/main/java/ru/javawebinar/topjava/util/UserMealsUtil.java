package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
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
    }

    // FIXME my implementation
    public static List<UserMealWithExceed> getFilteredMealsWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        return mealList.stream()
                .filter(userMeal -> {
                    LocalTime currentTime = userMeal.getDateTime().toLocalTime();
                    return currentTime.isAfter(startTime) && currentTime.isBefore(endTime);
                })
                .map(userMeal -> new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), (userMeal.getCalories() > caloriesPerDay)))
                .collect(Collectors.toList());
    }

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
}