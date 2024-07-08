package com.springboottest.springboottest;

import com.fasterxml.jackson.annotation.JsonView;
import com.springboottest.springboottest.models.Order;
import com.springboottest.springboottest.util.View;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

public class UserDTO {
    private int id;
    private String fullName;
    private int age;
    private String email;
    private List<Order> orders = new ArrayList<>();

    public UserDTO(int id, String fullName, int age, String email, List<Order> orders) {
        this.id = id;
        this.fullName = fullName;
        this.age = age;
        this.email = email;
        this.orders = orders;
    }
    public UserDTO() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
