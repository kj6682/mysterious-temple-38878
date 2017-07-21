package org.kj6682.peakycake;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.kj6682.commons.LocalDateDeserializer;
import org.kj6682.commons.LocalDateSerializer;
import org.springframework.util.Assert;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import java.time.LocalDate;

/**
 * Created by luigi on 12/07/2017.
 * <p>
 * THE model (use this to make business)
 */

@Data
@Entity(name = "peakyorder")
class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty
    private String shop;

    @NotEmpty
    private String product;

    @Min(value = 1L)
    private Integer quantity;

    @NotEmpty
    private String message;


    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate created;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate due;

    @NotEmpty
    private String status;

    protected Order() {
    }

    ;

    public Order(String shop,
                 String product,
                 Integer quantity,
                 String message,
                 LocalDate since,
                 LocalDate due,
                 String status) {

        Assert.notNull(shop, "an order needs a shop");
        Assert.notNull(product, "an order needs a product");
        Assert.notNull(quantity, "an order needs a quantity");
        Assert.isTrue(quantity.intValue() > 0, "an order needs a positive quantity");
        Assert.notNull(message, "an order needs a message");
        Assert.notNull(since, "an order needs an origin date");
        Assert.notNull(due, "an order needs an due date");
        Assert.notNull(status, "an order needs an state");

        this.shop = shop;
        this.product = product;
        this.quantity = quantity;
        this.message = message;
        this.created = since;
        this.due = due;
        this.status = status;
    }

}// :)
