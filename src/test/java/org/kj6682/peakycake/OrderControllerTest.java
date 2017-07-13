package org.kj6682.peakycake;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by luigi on 13/07/2017.
 */

@RunWith(SpringRunner.class)
@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @Autowired
    private MockMvc mvc;

    @MockBean
    private OrderRepository orderRepository;

    private List orderList;

    @Before
    public void setup() {
        orderList = new LinkedList<Order>();
        for (int i = 0; i < 10; i++) {
            orderList.add(new Order("North","cake : " + i,
                    "message : " + i,
                    LocalDate.now(),
                    LocalDate.now().plusDays(10),
                    "NEW"));
        }
    }

    @Test
    public void getAllOrdersList() throws Exception {

        given(this.orderRepository.findAll()).willReturn(Optional.of(orderList));

        this.mvc.perform(get("/api/orders")
                .accept(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(10)));

    }

    @Test
    public void getEmptyOrdersList() throws Exception {
        given(this.orderRepository.findAll()).willReturn(Optional.empty());

        this.mvc.perform(get("/api/orders")
                .accept(contentType))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void getNorthShopOrders() throws Exception{

        given(this.orderRepository.findByShop(anyString())).willReturn(Optional.of(orderList));

        this.mvc.perform(get("/api/north/orders")
                .accept(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(10)));

    }

    @Test
    public void getNowhereShopOrders() throws Exception{

        given(this.orderRepository.findByShop(anyString())).willReturn(Optional.empty());

        this.mvc.perform(get("/api/nowhere/orders")
                .accept(contentType))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", hasSize(0)));

    }

    @Test
    public void getNullShopOrders() throws Exception{

        given(this.orderRepository.findAll()).willReturn(Optional.empty());

        this.mvc.perform(get("/api//orders")
                .accept(contentType))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", hasSize(0)));

    }

    @Test
    public void createNewOrder() throws Exception{
    }

    @Test
    public void updateOrder() {

    }

    @Test
    public void deleteOrder() {

    }

}// :)
