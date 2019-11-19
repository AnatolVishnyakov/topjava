package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    private static final int MEAL_ID = START_SEQ;
    public static final Meal APPLE = new Meal(MEAL_ID, LocalDateTime.now(), "apple", 1200);
    public static final Meal BANANA = new Meal(MEAL_ID + 1, LocalDateTime.now(), "banana", 1400);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal...meals) {
        assertMatch(actual, Arrays.asList(meals));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("datetime").isEqualTo(expected);
    }
}
