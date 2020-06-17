package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
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
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Objects;

//import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
//import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

//import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
//import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

//    private MealRepository repository;
    ConfigurableApplicationContext appCtx;
    private MealRestController controller;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        controller = appCtx.getBean(MealRestController.class);
//            log.info("creat new Meals in init()");
            controller.setStartDate(LocalDate.MIN);
            controller.setEndDate(LocalDate.MAX);
            controller.setStartTime(LocalTime.MIN);
            controller.setEndTime(LocalTime.MAX);
    }

    @Override
    public void destroy() {
        appCtx.close();
        super.destroy();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        log.info("doPost");

        String id = request.getParameter("id");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");

        if (startDate != null || endDate != null || startTime != null || endTime != null) {
            controller.parseLocalDates(startDate, endDate);
            controller.parseLocalTimes(startTime, endTime);

            setAttributes(request);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }

        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        if(request.getParameter("id").isEmpty()) {
            controller.create(meal);
        } else {
            controller.update(meal);
        }
        response.sendRedirect("meals");
//        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("doGet");
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                controller.delete(id);
                setAttributes(request);
                response.sendRedirect("meals");
                break;
            case "create":
                System.out.println("CREATE");
                request.setAttribute("meal", new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000));
                setAttributes(request);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "update":
//                final Meal meal = controller.get(getId(request));
                request.setAttribute("meal", controller.get(getId(request)));
                setAttributes(request);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("getAll userId = ", SecurityUtil.authUserId());
                setAttributes(request);
                request.setAttribute("mealsTo",
                        MealsUtil.getTos(controller.getAll(), MealsUtil.DEFAULT_CALORIES_PER_DAY));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }

    private void setAttributes(HttpServletRequest request) {
        request.setAttribute("startDate", controller.getStartDate());
        request.setAttribute("endDate", controller.getEndDate());
        request.setAttribute("startTime", controller.getStartTime());
        request.setAttribute("endTime", controller.getEndTime());
        request.setAttribute("mealsTo", controller.getAllisBetweenDateTime());
    }
}
