package controllers;

import models.Order;
import models.Status;
import models.User;
import repositories.UsersRepository;
import services.UsersService;
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
class UsersControllerTest {
    @Mock
    private UsersService usersService;
    @Mock
    private UsersRepository usersRepository;
    @InjectMocks
    private UsersController usersController;

    @Test
    void getUsers_ReturnsListUser() throws Exception {
        User user = new User
                (9, "КАТЯ", 18, "gamaleec.00@mail.ru",
                        List.of(new Order(1, "telefon", Status.EXPECTED, 13545, this.usersController.getUsersById(9))), "klknfbx");
        List<User> users = new ArrayList<>();
        users.add(user);
        Mockito.when(usersController.getUsers()).thenReturn(users);

        var responseEntity = this.usersController.getUsers();

        assertNotNull(responseEntity);
        assertEquals(users, responseEntity);
    }

    @Test
    void getUsersById_ReternUser() {
        int id = 9;
        User user = new User
                (id, "КАТЯ", 18, "gamaleec.00@mail.ru",
                        List.of(new Order(1, "telefon", Status.EXPECTED, 13545, this.usersController.getUsersById(9))), "klknfbx");
        Mockito.when(usersController.getUsersById(id)).thenReturn(user);

        var responseEntity = this.usersController.getUsersById(id);

        assertNotNull(responseEntity);
        assertEquals(user, responseEntity);

    }

    @Test
    void create() {
        User user = new User
                (9, "КАТЯ", 18, "gamaleec.00@mail.ru",
                        List.of(new Order(1, "telefon", Status.EXPECTED, 13545, this.usersController.getUsersById(9))), "klknfbx");
        BindingResult bindingResult = new BeanPropertyBindingResult(user, "user");

        var responseEntity = this.usersController.create(user, bindingResult);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

    }

    @Test
    void update() {

        User user = new User
                (1, "КАТЯ", 18, "gamaleec.00@mail.ru",
                        List.of(new Order(1, "telefon", Status.EXPECTED, 13545, this.usersController.getUsersById(9))), "klknfbx");
        User user2 = new User
                (2, "kjbkjb", 56, "gamalebhgvhhjec.00@mail.ru",
                        List.of(new Order(1, "telefonknkn", Status.EXPECTED, 13545, this.usersController.getUsersById(9))), "klknfbx");
        BindingResult bindingResult = new BeanPropertyBindingResult(user, "user");


        var responseEntity = this.usersController.update(new User
                (10, "Варя", 19, "gamalekmlkma_varya.00@mail.ru", List.of(new Order(2, "telefon", Status.EXPECTED, 13545)), "jjnkjn"), bindingResult, 1);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void delete() {
        User user = new User
                (9, "КАТЯ", 18, "gamaleec.00@mail.ru",
                        List.of(new Order(1, "telefon", Status.EXPECTED, 13545, this.usersController.getUsersById(9))), "klknfbx");

        var responseEntity = this.usersController.delete(9);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}