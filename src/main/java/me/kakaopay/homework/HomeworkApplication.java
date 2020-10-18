package me.kakaopay.homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import me.kakaopay.homework.configration.RedisConfiguration;
import me.kakaopay.homework.configration.SwaggerConfiguration;

@EnableJpaAuditing
@Import({ RedisConfiguration.class, SwaggerConfiguration.class })
@SpringBootApplication
public class HomeworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomeworkApplication.class, args);
    }
}
