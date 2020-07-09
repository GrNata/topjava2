package ru.javawebinar.topjava.service.datajpa;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;

import static ru.javawebinar.topjava.Profiles.DATAJPA;
import static ru.javawebinar.topjava.Profiles.POSTGRES_DB;

@ActiveProfiles({POSTGRES_DB, DATAJPA})
public class DatajpaMealServiceTest extends AbstractMealServiceTest {
}
