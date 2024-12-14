package ru.kata.spring.boot_security.demo.service.impl;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.util.JavaRunner;

@Service
public class CodeExecutionService {

    // Шаблонный код, который будет предоставляться пользователю
    private static final String TEMPLATE_CODE =
            "public class Main {\n" +
                    "    public static void main(String[] args) {\n" +
                    "        // Напишите ваш код здесь\n" +
                    "    }\n" +
                    "}";

    public String getTemplateCode() {
        return TEMPLATE_CODE;
    }

    public String executeCode(String code) {
        try {
            String output = JavaRunner.run(code);
            // Проверяем, соответствует ли вывод ожидаемому результату
            if (output.trim().equals("Hello, World!")) {
                return "Успешно: Вывод соответствует ожидаемому результату!";
            } else {
                return "Ошибка: Ожидался вывод 'Hello, World!', но получен: " + output;
            }
        } catch (Exception e) {
            return "Ошибка выполнения: " + e.getMessage();
        }
    }
}


