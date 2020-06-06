package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;

public class MealTo {
    protected Integer id;
    protected final LocalDateTime dateTime;
    protected final String description;
    protected final int calories;
    protected final boolean excess;

    public MealTo(LocalDateTime dateTime, String description, int calories, boolean excess) {
        this.id = null;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.excess = excess;
//        this(null, dateTime, description, calories, excess);
    }

    public MealTo(int id, LocalDateTime dateTime, String description, int calories, boolean excess) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.excess = excess;
    }

    public MealTo(LocalDateTime dateTime, String description, int calories, int caloriesAllDay, int caloriesPerDay) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;

        if (caloriesAllDay > caloriesPerDay)
            this.excess = true;
        else
            this.excess = false;
    }



    @Override
    public String toString() {
        return "MealTo{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", excess=" + excess +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public boolean isExcess() {
        return excess;
    }
}
