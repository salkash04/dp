package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kata.spring.boot_security.demo.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {

}

