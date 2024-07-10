package controllers;


import com.fasterxml.jackson.annotation.JsonView;
import models.User;
import response.UserCreateException;
import services.UsersService;
import util.UserValidator;
import response.UsersErrorResponse;
import response.UsersNotFoundException;
import util.View;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")
public class UsersController {
    private UsersService usersService;
    private UserValidator userValidator;

    @Autowired
    public UsersController(UsersService usersService, UserValidator userValidator) {
        this.usersService = usersService;
        this.userValidator = userValidator;
    }

    @JsonView(View.UserDetails.class)
    @GetMapping()
    public List<User> getUsers() {
        return usersService.findAll();
    }

    @JsonView(View.UserSummary.class)
    @GetMapping("/{id}")
    public User getUsersById(@PathVariable("id") int id) {
        return usersService.findOne(id);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid User user, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            StringBuilder builder = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                builder.append(error.getField())
                        .append("---")
                        .append(error.getDefaultMessage())
                        .append(";");

            }
            throw new UserCreateException(errors.toString());
        }

        usersService.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity update(@RequestBody @Valid User user, BindingResult bindingResult,
                                 @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            StringBuilder builder = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                builder.append(error.getField()).append("-").append(error.getDefaultMessage()).append(";");
            }
            new UsersNotFoundException();
        }
        usersService.update(id, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {
        usersService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<UsersErrorResponse> handlerException(UsersNotFoundException e) {
        UsersErrorResponse response = new UsersErrorResponse("Человек с таким id не найден", System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<UsersErrorResponse> handlerException(UserCreateException e) {
        UsersErrorResponse response = new UsersErrorResponse(e.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
