package ru.kata.spring.boot_security.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Course;
import ru.kata.spring.boot_security.demo.repository.CourseRepository;

import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> findAll() {
        List<Course> courses = courseRepository.findAll();
        courses.forEach(course -> System.out.println(course.getId() + ": " + course.getName()));
        return courses;
    }
}
