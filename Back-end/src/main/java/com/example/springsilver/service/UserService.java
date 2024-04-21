package com.example.springsilver.service;

import com.example.springsilver.models.Users;
import com.example.springsilver.models.enumeration.UserRoles;
import org.springframework.stereotype.Service;

public interface UserService {

    Users register(String username, String first_name, String last_name,String email, String password, String repeatPassword, String image_pathurl);
    Users registerWithRole(String username, String first_name, String last_name,String email, String password, String repeatPassword, UserRoles role, String image_pathurl);
}
