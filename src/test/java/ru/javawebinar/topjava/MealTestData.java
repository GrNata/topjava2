package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int NOT_FOUND = 10;
//    public static final int ID = START_SEQ + 2;
    public static final int USER_MEAL_ID = START_SEQ + 2;
    public static final int ADMIN_MEAL_ID = START_SEQ + 9;

    public static final Meal USER_MEAL = new Meal(USER_MEAL_ID, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0),
            "Завтрак", 500);
    public static final Meal USER_MEAL2 = new Meal(USER_MEAL_ID+1, LocalDateTime.of(2020, Month.JANUARY, 30, 14, 00),
            "Обед", 1000);
    public static final Meal USER_MEAL3 = new Meal(USER_MEAL_ID+2, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 00),
            "Ужин", 500);
    public static final Meal USER_MEAL4 = new Meal(USER_MEAL_ID+3, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 00),
            "Еда на граничное значение", 100);
    public static final Meal USER_MEAL5 = new Meal(USER_MEAL_ID+4, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 00),
            "Завтрак", 500);
    public static final Meal USER_MEAL6 = new Meal(USER_MEAL_ID+5, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 00),
            "Обед", 1000);
    public static final Meal USER_MEAL7 = new Meal(USER_MEAL_ID+6, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 00),
            "Ужин", 510);
    public static final Meal ADMIN_MEAL = new Meal(ADMIN_MEAL_ID, LocalDateTime.of(2020, Month.JANUARY, 31, 14, 00),
        "Админ ланч", 510);
    public static final Meal ADMIN_MEAL9 = new Meal(ADMIN_MEAL_ID+1, LocalDateTime.of(2020, Month.JANUARY, 31, 21, 00),
            "Админ ужин", 1500);



    public static Meal getNew() {
        LocalDateTime dateTime = LocalDateTime.of(2020, Month.JANUARY, 05, 21, 0);
        return new Meal(null, dateTime, "new Eat", 0);
    }

    public static Meal getNewDouble() {
        LocalDateTime dateTime = LocalDateTime.of(2019, Month.APRIL, 04, 10, 0);
        return new Meal(null, dateTime, "new Eat", 100);
    }

    public static Meal getUpdated() {
        Meal update = new Meal(USER_MEAL);
//        update.setDateTime(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 00));
        update.setDescription("Обед update");
        update.setCalories(555);
        return update;
    }

    public static Meal updateDoubleDateTime() {
        Meal update = new Meal(USER_MEAL);
        update.setDateTime(LocalDateTime.of(2019, Month.APRIL, 04, 10, 00));
        update.setDescription("Обед up");
        update.setCalories(555);
        return update;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal...expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).isEqualTo(expected);
    }


}
