package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static List<UserMealWithExceed> getFilteredMealsWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        return mealList.stream()
                .filter(userMeal -> {
                    LocalTime currentTime = userMeal.getDateTime().toLocalTime();
                    return currentTime.isAfter(startTime) && currentTime.isBefore(endTime);
                })
                .map(userMeal -> new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), (userMeal.getCalories() > caloriesPerDay)))
                .collect(Collectors.toList());
    }
}