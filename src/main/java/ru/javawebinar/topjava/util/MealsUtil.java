package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.InMemoryMealRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class MealsUtil {

    public static void main(String[] args) {
//        int id = 0;
//        List<Meal> meals = Arrays.asList(
//                new Meal(id++, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
//                new Meal(id++, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
//                new Meal(id++, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
//                new Meal(id++, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
//                new Meal(id++, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
//                new Meal(id++, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
//                new Meal(id++, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
//        );

//        List<MealTo> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
//        mealsTo.forEach(System.out::println);

//        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<MealTo> filteredByCycles(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with excess. Implement by cycles
        return null;
    }

    public static List<MealTo> getWithExceed(Collection<Meal> meals, int caloriesPerDay) {
        return getFilteredByStreams(meals, LocalTime.MIN, LocalTime.MAX, 2000);
    }

    public static List<MealTo> getFilteredByStreams(Collection<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate =  meals.stream().
                collect(Collectors.groupingBy(Meal::getDate,
                        Collectors.summingInt(Meal::getCalories)));

        return meals.stream().
                filter(um -> TimeUtil.isBetweenHalfOpen(um.getDateTime().toLocalTime(), startTime, endTime)).
                map(meal -> creatTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay)).
                collect(Collectors.toList());
    }

    private static MealTo creatTo(Meal meal, boolean exceeded){
        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), exceeded);
    }

}
