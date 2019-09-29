package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

// Сервлет по отображению списка еды
public class MealServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("Start MealServlet [GET]");

        List<MealWithExceed> mealWithExceeds = MealsUtil.MEAL_LIST.stream()
                .map(meal -> new MealWithExceed(meal, meal.getCalories() > 600))
                .collect(Collectors.toList());

        request.setAttribute("meals", mealWithExceeds);
        request.getRequestDispatcher("/meals.jsp")
                .forward(request, response);

        logger.debug("End MealServlet");
    }
}
