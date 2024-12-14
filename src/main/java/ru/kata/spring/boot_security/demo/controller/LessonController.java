package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class LessonController {

    @GetMapping("/module1/lesson/{lessonId}")
    public String getLesson(@PathVariable int lessonId, Model model) {
        switch (lessonId) {
            case 1:
                model.addAttribute("lessonTitle", "Что такое Java? Основы языка");
                model.addAttribute("lessonContent", "Java — это объектно-ориентированный язык программирования...");
                return "lessons/lesson1"; // Возвращаем шаблон для первого урока
            case 2:
                model.addAttribute("lessonTitle", "Первая программа на Java");
                model.addAttribute("lessonContent", "В этом уроке вы научитесь писать вашу первую программу на Java...");
                return "lessons/lesson2"; // Возвращаем шаблон для второго урока
            // Добавьте другие уроки по аналогии...
            default:
                throw new RuntimeException("Урок не найден с ID: " + lessonId);
        }
    }
}
