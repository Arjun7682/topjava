package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.MealMapStorage;
import ru.javawebinar.topjava.storage.Storage;
import ru.javawebinar.topjava.util.Counter;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    private Storage storage = new MealMapStorage();

    {
        storage.save(new Meal(Counter.getId(), LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        storage.save(new Meal(Counter.getId(), LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        storage.save(new Meal(Counter.getId(), LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        storage.save(new Meal(Counter.getId(), LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        storage.save(new Meal(Counter.getId(), LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        storage.save(new Meal(Counter.getId(), LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        int id = Integer.parseInt(req.getParameter("id"));
        LocalDateTime localDateTime = LocalDateTime.parse(req.getParameter("date"));
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));

        Meal meal;
        if ((meal = storage.get(id)) != null) {
            meal.setDateTime(localDateTime);
            meal.setDescription(description);
            meal.setCalories(calories);
        } else {
            meal = new Meal(id, localDateTime, description, calories);
            storage.save(meal);
        }
        req.setAttribute("meals", MealsUtil.getWithExcess(storage.getAll(), 2000));
        req.getRequestDispatcher("/mealsList.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("redirect to meals");

        String action = req.getParameter("action");
        if (action == null) {
            req.setAttribute("meals", MealsUtil.getWithExcess(storage.getAll(), 2000));
            req.getRequestDispatcher("/mealsList.jsp").forward(req, resp);
            return;
        }

        Meal meal = null;
        String id = req.getParameter("id");
        switch (action) {
            case "delete":
                storage.delete(Integer.parseInt(id));
                resp.sendRedirect("meals");
                return;
            case "edit":
                meal = storage.get(Integer.parseInt(id));
                break;
            case "add":
                meal = new Meal(Counter.getId(), null, "", 0);
                break;
        }
        req.setAttribute("meal", meal);
        req.getRequestDispatcher("/editMeal.jsp").forward(req, resp);
    }
}
