package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// Сервлет по отображению списка еды
public class MealServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(MealServlet.class);
    private static List<Meal> meals = Arrays.asList(
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак. Кофе и блинчики.", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Комплексный обед.", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин. Картофель запеченный.", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак. Кофе и драники.", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед. Сырный суп.", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин. Макароны с сосиской.", 510)
    );

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("Start MealServlet");

        List<MealWithExceed> mealWithExceeds = meals.stream()
                .map(meal -> new MealWithExceed(meal, meal.getCalories() > 600))
                .collect(Collectors.toList());

        request.setAttribute("mealWithExceeds", mealWithExceeds);
        request.getRequestDispatcher("/WEB-INF/jsp/userMeals.jsp")
                .forward(request, response);

        logger.debug("End MealServlet");
    }
}
