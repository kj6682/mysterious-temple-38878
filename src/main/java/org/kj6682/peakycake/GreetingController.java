package org.kj6682.peakycake;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class GreetingController {


    @RequestMapping("/greeting")
    public Order greeting() {
        Order order = new Order("peaky cake",
                "peaky cake customizable message",
                LocalDate.of(2017, 7, 13),
                LocalDate.of(2017,8,7),
                "NEW");
        return order;
    }
}
