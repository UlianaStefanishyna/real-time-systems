package com.real.time.systems;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class RandomSignalApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(RandomSignalApplication.class, args);
    }

    @Override
    public void run(String... args) {
    }
}
