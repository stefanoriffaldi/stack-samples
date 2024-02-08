package org.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class MyApp implements ApplicationRunner {

    @Value("#{systemEnvironment['HERO']}")
    private String sysEnvNotation;

    @Value("${HERO}")
    private String simpleNotation;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("With    'systemEnvironment' notation : {}", sysEnvNotation);
        log.info("Without 'systemEnvironment' notation : {}", simpleNotation);
    }

    public static void main(String[] args) {
        SpringApplication.run(MyApp.class, args);
    }
}