package com.example.springsilver.models;


import com.example.springsilver.models.enumeration.UserRoles;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;
    @Column(nullable = false)
    private String first_name;
    private String last_name;
    @Column(nullable = false)
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}")
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, name = "userRoleId")
    private Integer userRoles;

    private String image_pathurl;

    @Column(name = "isActive", nullable = false)
    private boolean isActive;

    public Users(String username, Integer userRoles, String password, String email, String last_name, String first_name, String image_pathurl, boolean isActive) {
        this.username=username;
        this.userRoles = userRoles;
        this.password = password;
        this.email = email;
        this.first_name=first_name;
        this.last_name = last_name;
        this.image_pathurl = image_pathurl;
        this.isActive = isActive;
    }
}
