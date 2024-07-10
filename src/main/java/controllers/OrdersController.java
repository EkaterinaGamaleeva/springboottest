package controllers;


import com.fasterxml.jackson.annotation.JsonView;
import response.OrderCreateException;
import response.OrderErrorResponse;
import response.OrderNotFoundExcention;
import models.Order;
import services.OrdersService;
import services.UsersService;
import util.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/orders")
public class OrdersController {

    private final OrdersService ordersService;
    private final UsersService usersService;

    @Autowired
    public OrdersController(OrdersService ordersService, UsersService usersService) {
        this.ordersService = ordersService;
        this.usersService = usersService;
    }

    @JsonView(View.UserDetails.class)
    @GetMapping
    public List<Order> getFindAll() {
        return ordersService.findAll();

    }

    @JsonView(View.UserSummary.class)
    @GetMapping("/{id}")
    public Order findOne(@PathVariable("id") int id) {
        return ordersService.findOne(id);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid Order order, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder builder = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                builder.append(error.getField()).append("-").append(error.getDefaultMessage()).append(";");
                throw new OrderCreateException(errors.toString());
            }
        }
        ordersService.save(order);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid Order order, BindingResult bindingResult,
                                             @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            StringBuilder builder = new StringBuilder();
            List<FieldError> errors = (List<FieldError>) bindingResult.getFieldError();
            for (FieldError error : errors) {
                builder.append(error.getField()).append("-").append(error.getDefaultMessage()).append(";");
            }
            new OrderNotFoundExcention();
        }
        ordersService.update(id, order);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") int id) {
        ordersService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<OrderErrorResponse> handlerException(OrderNotFoundExcention e) {
        OrderErrorResponse response = new OrderErrorResponse("Заказ с таким id не найдем", System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<OrderErrorResponse> handlerException(OrderCreateException e) {
        OrderErrorResponse response = new OrderErrorResponse(e.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}