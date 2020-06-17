package ru.javawebinar.topjava.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//import static ru.javawebinar.topjava.util.DateTimeUtil.getEndExclusive;
//import static ru.javawebinar.topjava.util.DateTimeUtil.getStartInclusive;
import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

@Service
public class MealService {
    private Logger log = LoggerFactory.getLogger(MealService.class);

    private final MealRepository repository;

    @Autowired
    public MealService(MealRepository repository) {
        this.repository = repository;
    }


    public List<Meal> getAll(int userId) {
        log.info("getAll userId={}", userId);
//        return repository.getAll(userId);
        return repository.getAll().stream()
                        .filter(meal -> meal.getUserId() == userId)
                        .collect(Collectors.toList());
    }

    public Meal get(int id, int userId){
        log.info("get id={}, userId={}", id, userId);
        Meal meal = repository.get(id);
        if (meal.getUserId() != userId) {
            throw new NotFoundException("NotFoundException: this User has not this meal.");
        }
        return repository.get(id);
    }

    public void delete(int id, int userId) {
        log.info("delete id={}, uderId={}", id, userId);
        if(repository.get(id).getUserId() != userId) {
            throw  new NotFoundException("NotFoundException: this User has not this meal.");
        }
        repository.delete(id);
    }

    public Meal create(Meal meal, int userId) {
        meal.setUserId(userId);
        log.info("create meal={} with userId={}", meal, userId);
        return repository.save(meal);
    }

    public void update(Meal meal, int userId) {
        log.info("update meal: {} with userId={}", meal, userId);
        if (meal.getUserId() != userId) {
            throw  new NotFoundException("NotFoundException: this User has not this meal.");
        }
        repository.save(meal);
    }

    public List<MealTo> getAllWithExceeded(int userId) {
        log.info("getAllWithExceeded.");
        List<MealTo> mealTos = getFilteredByStreams(getAll(userId), LocalTime.MIN, LocalTime.MAX, DEFAULT_CALORIES_PER_DAY);
        if (mealTos.size() == 0) {
            throw new NotFoundException("NotFoundException: this user has'not this meal.");
        }
        return mealTos;
    }

    public List<MealTo> getFilteredByStreams(Collection<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                        .collect(Collectors.groupingBy(Meal::getDate,
                                Collectors.summingInt(Meal::getCalories)));
        return meals.stream()
                .filter(um -> DateTimeUtil.isBetweenHalfOpen(um.getTime(), startTime, endTime))
                .map(meal -> creatTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public List<MealTo>getFilterByDateAndTime(Collection<MealTo> mealTos, LocalDate startDate, LocalDate endDate,
                                              LocalTime startTime, LocalTime endTime) {
        return mealTos.stream()
                .filter(um -> DateTimeUtil.isBetweenHalfOpen(um.getDateTime().toLocalTime(), startTime, endTime)
                    && DateTimeUtil.isBetweenDate(um.getDateTime().toLocalDate(), startDate, endDate))
                .collect(Collectors.toList());
    }

    private MealTo creatTo(Meal meal, boolean exceeded){
        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), exceeded);
    }
}