package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<Meal> meals = Arrays.asList(
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );


        List<MealTo> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<MealTo> filteredByCycles(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with excess. Implement by cycles
        Map<LocalDate, Integer> mapMeal = new HashMap<>();
        List<MealTo> list = new ArrayList<>();
        MealTo mealWithExcess;
        int calory;

        for (Meal meal : meals) {
            if (mapMeal.get(meal.getDateTime().toLocalDate()) == null) {
                calory = meal.getCalories();
            } else {
                calory = mapMeal.get(meal.getDateTime().toLocalDate()) + meal.getCalories();
            }
            mapMeal.put(meal.getDateTime().toLocalDate(), calory);
        }

        for (Meal meal : meals) {
            if (meal.getDateTime().toLocalTime().isAfter(startTime)
                    && meal.getDateTime().toLocalTime().isBefore(endTime)) {
                mealWithExcess = new MealTo(meal.getDateTime(), meal.getDescription(), meal.getCalories()
                        , mapMeal.get(meal.getDateTime().toLocalDate()) > caloriesPerDay);
                list.add(mealWithExcess);
            }
        }
        return list;
    }

    public static List<MealTo> filteredByStreams(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams
        Map<LocalDate, Integer> mapMeal = meals.stream()
                .collect(Collectors.groupingBy(Meal::getDate,
                        Collectors.summingInt(Meal::getCalories)));

       List<MealTo> list = meals.stream()
                .filter(s -> s.getDateTime().toLocalTime().isAfter(startTime) && s.getDateTime().toLocalTime().isBefore(endTime))
                .map(s -> createTo(s, mapMeal.get(s.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
        return list;
    }

    private static MealTo createTo(Meal meals, boolean excess) {
        return new MealTo(meals.getDateTime(), meals.getDescription(), meals.getCalories(), excess);
    }
}
