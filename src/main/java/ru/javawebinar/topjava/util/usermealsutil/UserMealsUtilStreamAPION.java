package ru.javawebinar.topjava.util.usermealsutil;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;
import ru.javawebinar.topjava.util.TimeUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.function.Function.identity;

public class UserMealsUtilStreamAPION {
    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> userMeals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        final class Aggregate {
            private final List<UserMeal> dailyMeals = new ArrayList<>();
            private int dailySumOfCalories;

            private void accumulate(UserMeal userMeal) {
                dailySumOfCalories += userMeal.getCalories();
                if (TimeUtil.isBetween(userMeal.getTime(), startTime, endTime)) {
                    dailyMeals.add(userMeal);
                }
            }

            private Aggregate combine(Aggregate that) {
                dailySumOfCalories += that.dailySumOfCalories;
                dailyMeals.addAll(that.dailyMeals);
                return this;
            }

            private Stream<UserMealWithExceed> finisher() {
                final boolean exceeded = dailySumOfCalories > caloriesPerDay;
                return userMeals.stream().map(meal -> new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), exceeded));
            }
        }

        Collection<Stream<UserMealWithExceed>> values = userMeals.stream()
                .collect(Collectors.groupingBy(UserMeal::getDate,
                        Collector.of(Aggregate::new, Aggregate::accumulate, Aggregate::combine, Aggregate::finisher)))
                .values();
        return values.stream().flatMap(identity()).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        List<UserMeal> userMeals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );

        List<UserMealWithExceed> mealsWithExceeded = getFilteredWithExceeded(userMeals, LocalTime.of(7, 0), LocalTime.of(12, 0), 1000);
        mealsWithExceeded.forEach(System.out::println);
    }
}
