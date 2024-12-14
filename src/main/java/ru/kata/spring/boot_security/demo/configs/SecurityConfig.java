package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService; // Поле для UserService

    @Autowired
    public SecurityConfig(@Lazy UserService userService) {
        this.userService = userService; // Инициализация поля через конструктор
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/auth/register", "/auth/login", "/css/**", "/js/**").permitAll() // Разрешить доступ к страницам регистрации и логина
                .antMatchers("/admin/**").hasRole("ADMIN") // Доступ для администраторов
                .antMatchers("/users/**").hasAnyRole("ADMIN", "USER") // Доступ для пользователей и администраторов
                .anyRequest().authenticated() // Все остальные запросы требуют аутентификации
                .and()
                .formLogin()
                .loginPage("/auth/login") // Страница логина
                .loginProcessingUrl("/auth/login") // URL для обработки логина
                .defaultSuccessUrl("/dashboard", true) // Перенаправление на /dashboard после успешного входа
                .failureUrl("/auth/login?error=true") // Перенаправление на страницу логина с ошибкой
                .permitAll() // Разрешить доступ к странице логина всем пользователям
                .and()
                .logout()
                .logoutUrl("/auth/logout") // URL для выхода
                .logoutSuccessUrl("/auth/login?logout=true") // Перенаправление после выхода с сообщением
                .permitAll(); // Разрешить доступ к выходу всем пользователям
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder()); // Настройка менеджера аутентификации
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Использование BCrypt для хэширования паролей
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean(); // Возвращение AuthenticationManager для использования в других частях приложения, если необходимо.
    }
}


