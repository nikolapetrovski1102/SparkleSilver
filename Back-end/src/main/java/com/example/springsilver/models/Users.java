package com.example.springsilver.models;


import com.example.springsilver.models.enumeration.UserRoles;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "Users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private String first_name;
    private String last_name;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRoles userRoles;

    @OneToMany
    private List<Orders> orders;

    @OneToOne
    private Cart cart;


    public Users(UserRoles userRoles, String password, String email, String last_name, String first_name) {
        this.userRoles = userRoles;
        this.password = password;
        this.email = email;
        this.last_name = last_name;
        this.first_name = first_name;
        this.cart = new Cart();
    }
}
