package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.kata.spring.boot_security.demo.service.impl.CourseService;


@Controller
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/courses/{id}")
    public String viewCourse(@PathVariable Long id) {
        switch (id.intValue()) {
            case 1:
                return "java-basics"; // Редирект на страницу Java Basics
            case 2:
                return "spring-boot"; // Редирект на страницу Spring Boot
            case 3:
                return "database-design"; // Редирект на страницу Database Design
            default:
                throw new RuntimeException("Курс не найден с ID: " + id); // Обработка случая, когда курс не найден
        }
    }

}

