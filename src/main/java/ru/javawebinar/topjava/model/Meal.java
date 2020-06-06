package ru.javawebinar.topjava.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

public class Meal {
    private Integer id;
    private LocalDateTime dateTime;
    private String description;
    private int calories;

    public Meal() {}

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
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

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public Integer getId() {    return id; }



    public void setId(Integer id) {
        this.id = id;
    }

    public void setDateTime(String dateTime) {
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm").parse(dateTime);
            LocalDateTime dateTime2 = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();    //  ???? dateTime2
        } catch (ParseException e) {
            System.out.println("Exception Data");
            e.printStackTrace();
        }
        this.dateTime = LocalDateTime.parse(dateTime);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(String calories) {
        this.calories = Integer.parseInt(calories);
    }

    public boolean isNew() {
//        return id == null;
        if (id == null) return true;
        return false;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
