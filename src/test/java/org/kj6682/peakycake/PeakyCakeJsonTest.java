package org.kj6682.peakycake;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

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
public class PeakyCakeJsonTest {

    @Autowired
    private JacksonTester<PeakyCake> json;

    @Test
    public void serialise() throws Exception{
        PeakyCake cake = new PeakyCake("peaky cake",
                "peaky cake customizable message",
                LocalDate.of(2017,7,13),
                "RUNNING");

        System.out.println(this.json.write(cake));
        assertThat(this.json.write(cake)).isEqualTo(ResourceUtils.getFile("classpath:peakycake.json"));
    }


}
