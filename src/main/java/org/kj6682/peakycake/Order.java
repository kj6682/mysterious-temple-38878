package org.kj6682.peakycake;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.Setter;
import org.kj6682.commons.LocalDateDeserializer;
import org.kj6682.commons.LocalDateSerializer;
import org.springframework.util.Assert;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

/**
 * Created by luigi on 12/07/2017.
 * <p>
 * THE model (use this to make business)
 */

@Data
@Entity
class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String shop;

    private String cake;

    private String message;


    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate created;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate due;


    private String status;

    protected Order(){};

    public Order(String shop,
                 String cake,
                 String message,
                 LocalDate since,
                 LocalDate due,
                 String status) {

        Assert.notNull(shop, "an order needs a shop");
        Assert.notNull(cake, "an order needs a cake");
        Assert.notNull(message, "an order needs a message");
        Assert.notNull(since, "an order needs an origin date");
        Assert.notNull(due, "an order needs an due date");
        Assert.notNull(status, "an order needs an state");

        this.shop = shop;
        this.cake = cake;
        this.message = message;
        this.created = since;
        this.due = due;
        this.status = status;
    }

}// :)
