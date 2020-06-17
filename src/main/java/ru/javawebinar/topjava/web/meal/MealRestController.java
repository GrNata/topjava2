package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class MealRestController {
    private Logger log = LoggerFactory.getLogger(MealRestController.class);

    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;

    @Autowired
    private MealService service;

    public List<Meal> getAll() {
        log.info("getAll  userId = "+SecurityUtil.authUserId());
        return service.getAll(SecurityUtil.authUserId());
    }

    public Meal get(int id) {
        log.info("get id={}", id);
        return service.get(id, SecurityUtil.authUserId());
    }

    public Meal create(Meal meal) {
        log.info("greate meal: {}", meal);
        return service.create(meal, SecurityUtil.authUserId());
    }

    public void delete(int id) {
        log.info("delete id={} ", id);
        service.delete(id, SecurityUtil.authUserId());
    }

    public void update(Meal meal) {
        log.info("update meal: {}", meal);
        service.update(meal, SecurityUtil.authUserId());
    }


    //__________________MealTo_____________________________

    public List<MealTo> getAllWithExceeded() {
        log.info("getAllWithExceeded ");
        return service.getAllWithExceeded(SecurityUtil.authUserId());
    }

    public List<MealTo> getAllisBetweenDateTime() {
        log.info("getAllisBetweenDateTime userId = "+SecurityUtil.authUserId());
        return service.getFilterByDateAndTime(service.getAllWithExceeded(SecurityUtil.authUserId()),
                                                startDate,
                                                endDate,
                                                startTime,
                                                endTime);
    }
    //_____________________Date and Time__________________________

    public void parseLocalDates(String startDate, String endDate){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (startDate.isEmpty())
            setStartDate(LocalDate.MIN);
        else    setStartDate(LocalDate.parse(startDate, dateTimeFormatter));

        if (endDate.isEmpty())
            setEndDate(LocalDate.MAX);
        else setEndDate(LocalDate.parse(endDate, dateTimeFormatter));
    }

    public void parseLocalTimes(String startTime, String endTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        if (startTime.isEmpty())
            setStartTime(LocalTime.MIN);
        else setStartTime(LocalTime.parse(startTime, dateTimeFormatter));

        if (endTime.isEmpty())
            setEndTime(LocalTime.MAX);
        else setEndTime(LocalTime.parse(endTime, dateTimeFormatter));
    }



    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

}