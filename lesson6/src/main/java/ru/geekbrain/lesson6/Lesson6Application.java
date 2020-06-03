package ru.geekbrain.lesson6;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(value = "ru.geekbrain")
@EnableJpaRepositories("ru.geekbrain")
@ServletComponentScan
public class Lesson6Application {

    public static void main(String[] args) {
        SpringApplication.run(Lesson6Application.class, args);
    }

}
