package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({
        "jpa",
        "datajpa,jpa"})
public class JpaUserServiceTest extends UserServiceTest {
}
