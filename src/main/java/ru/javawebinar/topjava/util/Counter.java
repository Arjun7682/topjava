package ru.javawebinar.topjava.util;

import java.util.concurrent.atomic.AtomicInteger;

public class Counter {
    private static final AtomicInteger count = new AtomicInteger(0);

    public static int getId() {
        return count.getAndIncrement();
    }
}
