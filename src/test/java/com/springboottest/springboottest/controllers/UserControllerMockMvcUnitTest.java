package com.springboottest.springboottest.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboottest.springboottest.UserDTO;
import com.springboottest.springboottest.models.Order;
import com.springboottest.springboottest.models.Status;
import com.springboottest.springboottest.models.User;
import com.springboottest.springboottest.repositories.UsersRepository;
import javafx.application.Application;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerMockMvcUnitTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UsersRepository repository;
    @MockBean
    private UsersController controller;
    @Test
    public void getUserCreate_ReturnsJsonNotInfo()  throws Exception {
        var user = new User
                (9, "КАТЯ", 18, "gamaleec.00@mail.ru", (List.of( new Order(2,"hvjhvjhv",Status.EXPECTED,41564,controller.getUsersById(9)))),"hbjhbjh");

        Mockito.when(repository.save(Mockito.any())).thenReturn(user);
        MvcResult result = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.fullName").value(user.getFullName()))
                .andExpect(jsonPath("$.age").value(user.getAge()))
                .andExpect( jsonPath("$.orders").value(user.getOrders()))
                .andReturn();

        Assertions.assertThat(result).isNotNull();
        String userJson = result.getResponse().getContentAsString();
        Assertions.assertThat(userJson).isEqualToIgnoringCase(objectMapper
                .writeValueAsString(
                        new UserDTO(user.getId(),user.getFullName(),user.getAge(),user.getEmail(),user.getOrders())));
    }

}
