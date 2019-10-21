package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

// Сервлет по отображению списка еды
public class MealServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(MealServlet.class);
    private MealRestController controller;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try (ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            controller = context.getBean(MealRestController.class);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("filter") != null
                ? "filter" : request.getParameter("action") == null
                    ? "getAll" : request.getParameter("action");

        switch (action) {
            case "create":
            case "update":
                logger.info(action);
                Meal meal = "create".equals(action)
                        ? new Meal(LocalDateTime.now(), "", 1_000)
                        : controller.get(SecurityUtil.adminUser().getId(), getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/edit.jsp")
                        .forward(request, response);
                break;

            case "delete":
                int removeElement = getId(request);
                logger.info("Delete {}", removeElement);
                controller.delete(SecurityUtil.adminUser().getId(), removeElement);
                response.sendRedirect("meals");
                break;

            case "filter":
                String sd = request.getParameter("startDate");
                LocalDate startDate = sd.isEmpty()
                    ? LocalDate.of(1980, 1, 1)
                    : DateTimeUtil.toDate(sd).toLocalDate();

                String ed = request.getParameter("endDate");
                LocalDate endDate = ed.isEmpty()
                    ? LocalDate.now()
                    : DateTimeUtil.toDate(ed).toLocalDate();

                if (!sd.isEmpty() || !ed.isEmpty()) {
                    List<Meal> meals = controller.getAll(SecurityUtil.authUserId())
                            .stream().filter(meal1 -> DateTimeUtil.isBetween(meal1.getDate(), startDate, endDate))
                            .collect(Collectors.toList());
                    request.setAttribute("meals",
                            MealsUtil.getWithExcess(meals, MealsUtil.DEFAULT_CALORIES_PER_DAY));
                    request.getRequestDispatcher("/meals.jsp")
                            .forward(request, response);
                    break;
                }

            case "getAll":
            default:
                request.setAttribute("meals",
                        MealsUtil.getWithExcess(controller.getAll(SecurityUtil.adminUser().getId()), MealsUtil.DEFAULT_CALORIES_PER_DAY));
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
        controller.create(SecurityUtil.adminUser().getId(), meal);
        response.sendRedirect("meals");
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
