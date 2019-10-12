package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

public class SecurityUtil {
    private static final User ADMIN = new User(SecurityUtil.authUserId(), "Admin", "admin@com.ru", "admin", Role.ROLE_ADMIN);

    public static User admin() {
        return ADMIN;
    }

    public static int authUserId() {
        return 1;
    }

    public static int authUserCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }
}