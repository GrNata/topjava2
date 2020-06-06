package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.InMemoryMealRepository;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    private static String EDIT_FORM = "/formMeal.jsp";
    private static String LIST_MEALS = "/meals.jsp";

    MealRepository repository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        repository = new InMemoryMealRepository();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        int mealId;
        String forward = "";
        String action = request.getParameter("action");

        if (action == null || action.isEmpty())     action = "listMeals";
        request.setAttribute("localDateTimeFormat", new SimpleDateFormat("yyyy-MM-dd'T'hh:mm") );

        switch (action) {
            case "delete" :
                mealId = Integer.parseInt(request.getParameter("id"));
                log.info("delete meal id = " + mealId);
                forward = LIST_MEALS;
                repository.delete(mealId);
                request.setAttribute("mealTo", MealsUtil.getWithExceed(repository.getAll(), 2000));
                break;
            case "update" :
                mealId = Integer.parseInt(request.getParameter("id"));
                log.info("update meal id = " + mealId);
                forward = EDIT_FORM;
                request.setAttribute("meal", repository.get(mealId));
                break;
            case "new" :
                log.info("new meal");
                forward = EDIT_FORM;
                request.setAttribute("meal", new Meal(LocalDateTime.now(), "", 1000));
                break;
            case "listMeals" :
                log.info("list of meals");
                forward = LIST_MEALS;
                request.setAttribute("mealTo", MealsUtil.getWithExceed(repository.getAll(), 2000));
//                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
        request.getRequestDispatcher(forward).forward(request, response);

//        request.getRequestDispatcher("/meals.jsp").forward(request, response);
//        response.sendRedirect("meals.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        Meal meal = new Meal(id.isEmpty() ? null : Integer.parseInt(id),
                                LocalDateTime.parse(request.getParameter("date")),
                                request.getParameter("description"),
                                Integer.parseInt(request.getParameter("calories")));
        log.info("POST ",  meal.isNew() ? "Create {}" : "Update {}", meal);
        repository.save(meal);

        request.setAttribute("localDateTimeFormat", new SimpleDateFormat("yyyy-MM-dd'T'hh:mm") );
        request.setAttribute("mealTo", MealsUtil.getWithExceed(repository.getAll(), 2000));
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}
