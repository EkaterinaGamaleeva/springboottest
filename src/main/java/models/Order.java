package models;


import com.fasterxml.jackson.annotation.JsonView;
import util.View;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonView(View.UserDetails.class)
    private int id;


    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    @Column(name = "name")
    @JsonView(View.UserDetails.class)
    private String name;


    @Enumerated(EnumType.STRING)
    @JsonView(View.UserDetails.class)
    private Status status;

    @Min(value = 40, message = "Age should be greater than 120")
    @Column(name = "sum")
    @JsonView(View.UserSummary.class)
    private int sum;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonView(View.UserDetails.class)
    private User owner;
    public Order() {
    }


    public Order(int i, String telefon, Status expected, int i1) {
    }

    public Order(int id, String name, Status status, int sum, User owner) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.sum = sum;
        this.owner = owner;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
