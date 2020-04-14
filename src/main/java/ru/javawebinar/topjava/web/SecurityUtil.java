package ru.javawebinar.topjava.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import static java.util.Objects.requireNonNull;

public class SecurityUtil {
    private SecurityUtil() {
    }

    private static final User ADMIN = new User(SecurityUtil.authUserId(), "Admin", "admin@com.ru", "admin", Role.ROLE_ADMIN);

    public static User adminUser() {
        return ADMIN;
    }

    public static AuthorizedUser safeGet() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        Object principal = auth.getPrincipal();
        return (principal instanceof AuthorizedUser) ? (AuthorizedUser) principal : null;
    }

    public static AuthorizedUser get() {
        AuthorizedUser user = safeGet();
        requireNonNull(user, "No authorized user found");
        return user;
    }

    public static int authUserId() {
        return get().getUserTo().getId();
    }

    public static int authUserCaloriesPerDay() {
        return get().getUserTo().getCaloriesPerDay();
    }
}