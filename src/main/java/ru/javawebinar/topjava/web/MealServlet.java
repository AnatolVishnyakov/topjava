package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.InMemoryMealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

// Сервлет по отображению списка еды
public class MealServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(MealServlet.class);
    private InMemoryMealRepository repository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        repository = new InMemoryMealRepository();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action") == null
                ? "getAll" : request.getParameter("action");

        switch (action) {
            case "create":
            case "update":
                logger.info(action);
                Meal meal = "create".equals(action)
                        ? new Meal(LocalDateTime.now(), "", 1_000)
                        : repository.get(SecurityUtil.admin(), getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/edit.jsp")
                        .forward(request, response);
                break;

            case "delete":
                int removeElement = getId(request);
                logger.info("Delete {}", removeElement);
                repository.delete(SecurityUtil.admin(), removeElement);
                response.sendRedirect("meals");
                break;

            case "getAll":
            default:
                request.setAttribute("meals",
                        MealsUtil.getWithExceeded(repository.getAll(SecurityUtil.admin()), MealsUtil.DEFAULT_CALORIES_PER_DAY));
                request.getRequestDispatcher("/meals.jsp")
                        .forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");

        String id = request.getParameter("id");
        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        logger.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        repository.save(SecurityUtil.admin(), meal);
        response.sendRedirect("meals");
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
