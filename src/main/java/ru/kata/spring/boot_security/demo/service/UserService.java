package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    User registerUser(User user); // Регистрация нового пользователя

    User findById(Long id); // Поиск пользователя по ID

    List<User> findAll(); // Получение списка всех пользователей

    void saveUser(User user); // Сохранение пользователя (обновление)

    void deleteById(long id); // Удаление пользователя по ID

    User findByUsername(String username); // Поиск пользователя по имени

    List<Role> findByRoleName(String role); // Поиск ролей по имени (если необходимо)

    List<Role> getAllRoles(); // Получение всех ролей (если необходимо)
}
