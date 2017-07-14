package org.kj6682.peakycake;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.Assert.*;
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

    private List<Order> orderList;

    @Before
    public void setup() {
        orderList = new LinkedList<Order>();
        for (int i = 0; i < 10; i++) {
            orderList.add(new Order("North","cake : " + i,"message : " + i, LocalDate.now(), LocalDate.now().plusDays(10), "NEW"));
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

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }


    @Test
    public void createNewOrder() throws Exception {

        Order order = orderList.get(0);
        String orderJson = json(order);

        given(this.orderRepository.save(order)).willReturn(order);


        this.mvc.perform(post("/api/{shop}/orders", "North")
                .contentType(contentType).content(orderJson))
                .andExpect(status().isCreated());

    }

    @Test
    public void createNewOrderFail() throws Exception {

        Order order = orderList.get(0);
        String orderJson = json(order);

        given(this.orderRepository.save(order)).willReturn(order);


        this.mvc.perform(post("/api/{shop}/orders", "South")
                .contentType(contentType).content(orderJson))
                .andExpect(status().isForbidden());

    }

    @Test
    public void updateOrderStatus() throws Exception{

        Order order = orderList.get(0);
        String orderJson = json(order);

        given(this.orderRepository.findById(anyLong())).willReturn(Optional.of(order));

        given(this.orderRepository.save(order)).will(new Answer<Order>() {
            @Override
            public Order answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                Order order1 = ((Order)args[0]);
                order1.setStatus("DONE");
                return  order1;
            };
        });

        this.mvc.perform(put("/api/orders/{id}", 1L)
                .contentType(contentType).content(orderJson))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("DONE"));;


    }

    @Test
    public void deleteOrder() throws Exception{

        this.mvc.perform(delete("/api/orders/{id}", 1L)
                .contentType(contentType).content("{'id':1}"))
                .andExpect(status().isOk());

    }

}// :)
