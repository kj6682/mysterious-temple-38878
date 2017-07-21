package org.kj6682.peakycake;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by luigi on 12/07/2017.
 *
 * TDD - use this test to define the ORDER model
 *
 */
@RunWith(SpringRunner.class)
@JsonTest
public class ProductJsonTest {

    @Autowired
    private JacksonTester<Product> json;

    @MockBean
    private OrderRepository orderRepository;

    Product cake;

    File jsonFile;

    @Before
    public void setup() throws Exception{
        cake = new Product("peaky product",
                "peaky product customizable message",
                LocalDate.of(2017,7,13),
                "RUNNING");
        jsonFile = ResourceUtils.getFile("classpath:peakycake.json");

    }
    @Test
    public void serialise() throws Exception{

        assertThat(this.json.write(cake)).isEqualTo(jsonFile);
    }
    @Test
    public void deserialise() throws Exception {

        String jsonObject = new String(Files.readAllBytes(jsonFile.toPath()));
        Product newCake = this.json.parse(jsonObject).getObject();
        assertThat(newCake.equals(cake));

    }

}
