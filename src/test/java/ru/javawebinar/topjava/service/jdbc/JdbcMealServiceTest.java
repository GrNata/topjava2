package ru.javawebinar.topjava.service.jdbc;

import ru.javawebinar.topjava.service.AbstractMealServiceTest;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

//@ActiveProfiles({POSTGRES_DB, JDBC})
public abstract class JdbcMealServiceTest extends AbstractMealServiceTest {

    public Date getDate(LocalDateTime dateTime) {
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
