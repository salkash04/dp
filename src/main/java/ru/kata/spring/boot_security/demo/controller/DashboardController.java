package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.service.impl.CourseService;

@Controller
public class DashboardController {

    private final CourseService courseService;

    @Autowired
    public DashboardController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("courses", courseService.findAll());
        return "dashboard"; // Возвращает HTML-шаблон "dashboard.html"
    }
}
