package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class JspMealController {

    @Autowired
    private MealService service;

    @PostMapping("/meals")
    public String doPost(HttpServletRequest request) throws UnsupportedEncodingException {
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        String id = request.getParameter("id");
        int userId = SecurityUtil.authUserId();
        if (StringUtils.isEmpty(id)) {
            checkNew(meal);
            service.create(meal, userId);
        } else {
            assureIdConsistent(meal, Integer.parseInt(id));
            service.update(meal, userId);
        }
        return "redirect:meals";
    }

    @GetMapping("/meals")
    public String meals(Model model) {
        int userId = SecurityUtil.authUserId();
        model.addAttribute("meals", MealsUtil.getWithExcess(service.getAll(userId), SecurityUtil.authUserCaloriesPerDay()));
        return "meals";
    }

    @GetMapping("/delete")
    protected String delete(HttpServletRequest request) {
        service.delete(getId(request), SecurityUtil.authUserId());
        return "redirect:meals";
    }

    @GetMapping("/update")
    protected String update(HttpServletRequest request, Model model) {
        final Meal meal = service.get(getId(request), SecurityUtil.authUserId());
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("/create")
    protected String create(Model model) {
        final Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("/filter")
    protected String filter(HttpServletRequest request, Model model) {
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        model.addAttribute("meals", getBetween(startDate, startTime, endDate, endTime));
        return "meals";
    }

    public List<MealTo> getBetween(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        int userId = SecurityUtil.authUserId();

        List<Meal> mealsDateFiltered = service.getBetweenDates(startDate, endDate, userId);
        return MealsUtil.getFilteredWithExcess(mealsDateFiltered, SecurityUtil.authUserCaloriesPerDay(), startTime, endTime);
    }

    private int getId(HttpServletRequest request) {
        return Integer.parseInt(request.getParameter("id"));
    }
}
