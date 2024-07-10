package models;

import com.fasterxml.jackson.annotation.JsonView;

import util.View;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonView(View.UserDetails.class)
    private int id;

    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 100, message = "Имя должно быть от 2 до 100 символов длиной")
    @Column(name = "full_name")
    @JsonView(View.UserDetails.class)
    private String fullName;
    @Min(value = 14, message = "Age should be greater than 14")
    @Column(name = "age")
    @JsonView(View.UserDetails.class)
    private int age;
    @NotEmpty(message = "Email should not be empty")
    @Column(name = "email")
    @Email
    @JsonView(View.UserDetails.class)
    private String email;

    @JsonView(View.UserDetails.class)
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    @JsonView(View.UserSummary.class)
    @Column(name = "info")
    private String infoOrder;

    public User() {
    }


    public <E> User(int i, String катя, int i1, String s, List<E> telefon, String hbjhbjh) {
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

    public String getInfoOrder() {
        return infoOrder;
    }

    public void setInfoOrder(String infoOrder) {
        this.infoOrder = infoOrder;
    }
}
