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
            Arrays.asList("blue cake, red cake, white cake, yellow cake, black cake".split(",")).forEach(
                    cake -> {
                        Order o1 = new Order("north", cake, "message", LocalDate.now(), LocalDate.now().plusDays(10), "NEW");
                        Order o2 = new Order("south", cake, "message", LocalDate.now(), LocalDate.now().plusDays(10), "NEW");
                        Order o3 = new Order("east", cake, "message", LocalDate.now(), LocalDate.now().plusDays(10), "NEW");
                        Order o4 = new Order("west", cake, "message", LocalDate.now(), LocalDate.now().plusDays(10), "NEW");

                        orderRepository.save(o1);
                        orderRepository.save(o2);
                        orderRepository.save(o3);
                        orderRepository.save(o4);

                    }
            );

        };
    }

}