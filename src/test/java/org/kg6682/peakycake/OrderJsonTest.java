package org.kg6682.peakycake;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.*;
import java.time.LocalDate;
import java.util.Date;


import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by luigi on 12/07/2017.
 *
 * TDD - use this test to define the ORDER model
 *
 */
@RunWith(SpringRunner.class)
@JsonTest
public class OrderJsonTest {

    @Autowired
    private JacksonTester<Order> json;

    @Test
    public void serialise() throws Exception{
        Order order = new Order("peaky cake",
                "peaky cake customizable message",
                LocalDate.of(2017,7,13),
                LocalDate.of(2017,8,7),
                "NEW");

        System.out.println(this.json.write(order));
/*
        assertThat(this.json.write(order)).isEqualTo(
                "{\n" +
                "  \"cake\" : \"peaky cake\",\n" +
                "  \"message\": \"peaky cake customizable message\",\n" +
                "  \"since\" : \"2017-07-13\",\n" +
                "  \"due\" : \"2017-08-07\",\n" +
                "  \"status\": \"NEW\"\n" +
                "}");
*/
    }
}
