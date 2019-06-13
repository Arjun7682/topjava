package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealMapStorage implements Storage {
    private ConcurrentMap<Integer, Meal> meals = new ConcurrentHashMap<>();
    private static final AtomicInteger count = new AtomicInteger(0);

    @Override
    public void save(Meal meal) {
        meal.setId(getId());
        meals.put(meal.getId(), meal);
    }

    @Override
    public Meal get(int id) {
        return meals.get(id);
    }

    @Override
    public void update(Meal meal) {
        meals.replace(meal.getId(), meal);
    }

    @Override
    public void delete(int id) {
        meals.remove(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(meals.values());
    }

    private int getId() {
        return count.getAndIncrement();
    }
}
