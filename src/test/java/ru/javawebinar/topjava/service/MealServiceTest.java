package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void create() {
        Meal newMeal = new Meal(null, LocalDateTime.now(), "Ужин", 800);
        newMeal.setUserId(USER_ID);
        Meal created = service.create(USER_ID, newMeal);
        newMeal.setId(created.getId());
        assertMatch(service.getAll(USER_ID), APPLE, BANANA, newMeal);
    }

    @Test
    public void delete() {
        service.delete(BANANA.getId(), USER_ID);
        assertMatch(service.getAll(USER_ID), APPLE);
    }

    @Test
    public void get() {
        assertMatch(service.get(APPLE.getId(), USER_ID), APPLE);
    }

    @Test
    public void update() {
    }

    @Test
    public void getAll() {
    }

    @Test
    public void getBetweenDateTimes() {
    }

    @Test
    public void getBetweenDates() {
    }
}