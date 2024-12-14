package ru.kata.spring.boot_security.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.service.impl.CodeExecutionService;
import ru.kata.spring.boot_security.demo.util.JavaRunner;

import java.util.Map;

@RestController
@RequestMapping("/api/compiler")
public class CompilerController {
    private final CodeExecutionService codeExecutionService;

    public CompilerController(CodeExecutionService codeExecutionService) {
        this.codeExecutionService = codeExecutionService;
    }

    // Эндпоинт для получения шаблонного кода
    @GetMapping("/template")
    public String getTemplateCode() {
        return codeExecutionService.getTemplateCode();
    }

    // Эндпоинт для выполнения пользовательского кода
    @PostMapping("/run")
    public String runCode(@RequestBody String code) {
        return codeExecutionService.executeCode(code);
    }
}

