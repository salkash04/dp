package ru.kata.spring.boot_security.demo.util;

import javax.tools.*;
import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.*;

public class JavaRunner {

    private static final String CLASS_NAME = "Main";
    private static final String FILE_NAME = CLASS_NAME + ".java";

    public static String run(String code) throws Exception {
        // Создаем временную директорию для работы
        File tempDir = Files.createTempDirectory("java-runner").toFile();
        tempDir.deleteOnExit();

        // Создаем файл с Java-кодом
        File sourceFile = new File(tempDir, FILE_NAME);
        try (FileWriter writer = new FileWriter(sourceFile)) {
            writer.write(code);
        }

        // Компилируем файл
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            throw new IllegalStateException("Java Compiler not available. Make sure you're using JDK, not JRE.");
        }

        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);

        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(List.of(sourceFile));
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, null, null, compilationUnits);

        boolean success = task.call();
        fileManager.close();

        if (!success) {
            StringBuilder errorMessage = new StringBuilder("Compilation failed:\n");
            for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
                errorMessage.append(diagnostic.getMessage(null)).append("\n");
            }
            throw new RuntimeException(errorMessage.toString());
        }

        // Запускаем скомпилированный класс
        return executeCompiledClass(tempDir, CLASS_NAME);
    }

    private static String executeCompiledClass(File tempDir, String className) throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder(
                "java", "-cp", tempDir.getAbsolutePath(), className
        );
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();
        boolean finished = process.waitFor(5, TimeUnit.SECONDS); // Ограничение по времени

        if (!finished) {
            process.destroy();
            throw new RuntimeException("Execution timed out.");
        }

        // Чтение результата выполнения
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            return output.toString().trim();
        }
    }
}
