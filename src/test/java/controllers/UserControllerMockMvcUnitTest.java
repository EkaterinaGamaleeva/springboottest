package controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import models.Order;
import models.Status;
import models.User;
import repositories.UsersRepository;
import services.UsersService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UsersController.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ContextConfiguration(classes = {UsersController.class})
public class UserControllerMockMvcUnitTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UsersRepository repository;

    @MockBean
    private UsersController controller;
    @MockBean
    private UsersService service;
    @Test
    public void getUser_ReturnsJsonNotInfoOrder()  throws Exception {
        var user = new User(9, "КАТЯ", 18, "gamaleec.00@mail.ru", (List.of( new Order(2,"hvjhvjhv", Status.EXPECTED,41564,controller.getUsersById(9)))),"hbjhbjh");
        List<User> users= new ArrayList<>();
        users.add(user);
        Mockito.when(this.service.findAll()).thenReturn(users);
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.fullName").value(user.getFullName()))
                .andExpect(jsonPath("$.age").value(user.getAge()))
                .andExpect( jsonPath("$.orders").value(user.getOrders()))
                .andReturn();
    }
    @Test
    public void getUsersById_whenGetExistingPerson_thenStatus200andUserReturned() throws Exception {
        var user = new User(9, "КАТЯ", 18, "gamaleec.00@mail.ru", (List.of( new Order(2,"hvjhvjhv", Status.EXPECTED,41564,controller.getUsersById(9)))),"hbjhbjh");
        Mockito.when(this.service.findOne(9)).thenReturn(user);
        mockMvc.perform(get("/users/9"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.fullName").value(user.getFullName()))
                .andExpect(jsonPath("$.age").value(user.getAge()))
                .andExpect( jsonPath("$.orders").value(user.getOrders()))
                .andExpect( jsonPath("$.infoOrder").value(user.getInfoOrder()))
                .andReturn();
    }

}
