package controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import models.Order;
import models.Status;
import models.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import repositories.OrdersRepository;
import repositories.UsersRepository;
import services.OrdersService;
import services.UsersService;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrdersController.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ContextConfiguration(classes = {UsersController.class})
public class OrderControllerMockMvcUnitTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private OrdersRepository repository;

    @MockBean
    private OrdersController controller;
    @MockBean
    private OrdersService service;

    @Test
    public void getUser_ReturnsJsonNotInfoOrder() throws Exception {
        Order order = new Order(1, "telefon", Status.EXPECTED, 1354);
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        Mockito.when(service.findAll()).thenReturn(orders);
        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(order.getId()))
                .andExpect(jsonPath("$.name").value(order.getName()))
                .andExpect(jsonPath("$.status").value(order.getStatus()))
                .andExpect(jsonPath("$.sum").value(order.getSum()))
                .andExpect(jsonPath("$.owner").value(order.getOwner()))
                .andReturn();
    }
}