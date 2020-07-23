package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static ru.javawebinar.topjava.util.ValidationUtil.*;

@Controller
@RequestMapping(value = "/meals")
public class JspMealController extends AbstractMealController {

    @Autowired
    private MealService service;

    @GetMapping("/delete")
    public String deleteMeal(HttpServletRequest request) {
        super.delete(getId(request));
        return "redirect:/meals";
    }

    @GetMapping("/create")
    public String createMeal(Model model) {
        Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("/update")
    public String updateMeal(@RequestParam String id, Model model) {
        int mealId = Integer.parseInt(id);
        int userId = SecurityUtil.authUserId();
        Meal meal = service.get(mealId, userId);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @PostMapping
    public String createOrUpdate(HttpServletRequest request) {
        Meal meal = new Meal(LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));
        int userId = SecurityUtil.authUserId();
        if (request.getParameter("id").isEmpty()) {
            service.create(meal, userId);
            System.out.println("NEW MEAL: "+meal);
        } else {
            int mealId = Integer.valueOf(request.getParameter("id"));
            assureIdConsistent(meal, mealId);
            service.update(meal, userId);
            System.out.println("UPDATE MEAL: "+meal);
        }
        return "redirect:/meals";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}
