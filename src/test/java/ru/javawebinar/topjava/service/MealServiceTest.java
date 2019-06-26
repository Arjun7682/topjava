package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;

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
    public void get() {
        Meal meal = service.get(MEAL_1_ID, ADMIN_ID);
        MealTestData.assertMatch(meal, MEAL_1);
    }

    @Test
    public void delete() {
        service.delete(MEAL_1_ID, ADMIN_ID);
        assertMatch(service.getAll(ADMIN_ID), MEAL_2);
    }

    @Test
    public void getBetweenDates() {
        List<Meal> meals = service.getBetweenDates(LocalDate.of(2015, 1, 6),
                LocalDate.of(2015, 1, 6), ADMIN_ID);
        assertMatch(meals, MEAL_1);
    }

    @Test
    public void getBetweenDateTimes() {
        List<Meal> meals = service.getBetweenDateTimes(LocalDateTime.of(2015, 1, 6, 10, 0),
                LocalDateTime.of(2015, 1, 7, 15,0), ADMIN_ID);
        assertMatch(meals, MEAL_1);
    }

    @Test
    public void getAll() {
        assertMatch(service.getAll(ADMIN_ID), MEAL_2, MEAL_1);
    }

    @Test
    public void update() {
        Meal updated = new Meal(MEAL_1);
        updated.setDescription("Updated");
        updated.setCalories(330);
        service.update(updated, ADMIN_ID);
        assertMatch(service.get(MEAL_1_ID, ADMIN_ID), updated);
    }

    @Test
    public void create() {
    }
}
