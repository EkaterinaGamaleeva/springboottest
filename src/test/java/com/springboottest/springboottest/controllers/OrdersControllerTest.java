package com.springboottest.springboottest.controllers;

import com.springboottest.springboottest.models.Order;
import com.springboottest.springboottest.models.Status;
import com.springboottest.springboottest.models.User;
import com.springboottest.springboottest.repositories.OrdersRepository;
import com.springboottest.springboottest.services.OrdersService;
import com.springboottest.springboottest.services.UsersService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrdersControllerTest {
    @Mock
    private OrdersService ordersService;
    @Mock
    private UsersService usersService;
    @Mock
    private OrdersRepository ordersRepository;
    @InjectMocks
    private OrdersController ordersController;

    @Test
    void getFindAll() {
        Order order = new Order(1, "telefon", Status.EXPECTED, 1354);
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        Mockito.when(ordersService.findAll()).thenReturn(orders);

        var responseEntity = this.ordersController.getFindAll();

        assertNotNull(responseEntity);
        assertEquals(orders, responseEntity);
    }

    @Test
    void findOne() {
        Order order = new Order(1, "telefon", Status.EXPECTED, 1354);
        Mockito.when(ordersService.findOne(1)).thenReturn(order);

        var responseEntity = this.ordersController.findOne(1);

        assertNotNull(responseEntity);
        assertEquals(order, responseEntity);
    }

    @Test
    void create() {
        Order order = new Order(1, "telefon", Status.EXPECTED, 1354);
        BindingResult bindingResult = new BeanPropertyBindingResult(order, "otder");

        var responseEntity = this.ordersController.create(order, bindingResult);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    void update() {
        Order order = new Order(1, "telefon", Status.EXPECTED, 1354);
        BindingResult bindingResult = new BeanPropertyBindingResult(order, "order");

        var responseEntity = this.ordersController.update(new Order(2, "telefonzsfzsc", Status.EXPECTED, 1453545), bindingResult, 1);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void delete() {
        Order order = new Order(1, "telefon", Status.EXPECTED, 1354);
        var responseEntity = this.ordersController.delete(1);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}