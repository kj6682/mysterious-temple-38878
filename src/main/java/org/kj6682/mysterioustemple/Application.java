package org.kj6682.mysterioustemple;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
import java.util.Arrays;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Profile({"ddl-create", "h2"})
    @Bean
    CommandLineRunner initOrders(OrderRepository orderRepository) {

        return (evt) -> {
            Arrays.asList("blue product,red product,white product,yellow product,black product".split(",")).forEach(
                    cake -> {
                        Order o1 = new Order("north", cake, 10, "message", LocalDate.now(), LocalDate.now().plusDays(10), "NEW");
                        Order o2 = new Order("south", cake, 10, "message", LocalDate.now(), LocalDate.now().plusDays(10), "NEW");
                        Order o3 = new Order("east", cake, 10, "message", LocalDate.now(), LocalDate.now().plusDays(10), "NEW");
                        Order o4 = new Order("west", cake, 10, "message", LocalDate.now(), LocalDate.now().plusDays(10), "NEW");

                        orderRepository.save(o1);
                        orderRepository.save(o2);
                        orderRepository.save(o3);
                        orderRepository.save(o4);

                    }
            );

        };
    }

    @Profile({"ddl-create", "h2"})
    @Bean
    CommandLineRunner initProducts(ProductRepository productRepository) {

        return (evt) -> {
            Arrays.asList("blue product, red product, white product, yellow product, black product".split(",")).forEach(
                    cake -> {
                        Product p1 = new Product(cake, "this is the " + cake, LocalDate.now(), "VALID");

                        productRepository.save(p1);

                    }
            );

        };
    }
}