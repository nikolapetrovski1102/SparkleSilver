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
    @Column(name = "user_id")
    //Dodadeno username da bide kluc vo tabelata
    private String username;

    private String first_name;
    private String last_name;
    private String email;

    //TODO Encrypt password
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRoles userRoles;

    @OneToMany
    private List<Orders> orders;

    @OneToOne
    private Cart cart;


    public Users(String username,UserRoles userRoles, String password, String email, String last_name, String first_name) {
        this.username=username;
        this.userRoles = userRoles;
        this.password = password;
        this.email = email;
        this.first_name=first_name;
        this.last_name = last_name;
        this.cart = new Cart();
    }
}
