package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.datajpa.DataJpaMealRepository;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

import static ru.javawebinar.topjava.Profiles.DATAJPA;
import static ru.javawebinar.topjava.Profiles.POSTGRES_DB;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles({POSTGRES_DB, DATAJPA})
public class DatajpaUserServiceTest extends AbstractUserServiceTest {

    @Test
    public void getWithMeals() throws Exception {
        User user = service.getWithMeals(USER_ID);
        System.out.println("USER: "+user);
        System.out.println("USER_MEALS: ");
        for (Meal m : user.getMeals()) System.out.println(m.getDescription()+" - "+m.getDateTime());
    }
}
