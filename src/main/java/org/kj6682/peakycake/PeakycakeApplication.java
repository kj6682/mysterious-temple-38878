package org.kj6682.peakycake;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Arrays;

@SpringBootApplication
public class PeakycakeApplication {

    public static void main(String[] args) {
        SpringApplication.run(PeakycakeApplication.class, args);
    }

    @Bean
    CommandLineRunner init(OrderRepository orderRepository) {

        return (evt) -> {
            orderRepository.save(new Order("north", "cake", "message", LocalDate.now(), LocalDate.now().plusDays(10), "NEW"));
            orderRepository.save(new Order("south", "cake", "message", LocalDate.now(), LocalDate.now().plusDays(10), "NEW"));

                        };
    }

}
