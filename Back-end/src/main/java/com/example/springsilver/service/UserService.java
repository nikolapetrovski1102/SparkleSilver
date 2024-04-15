package com.example.springsilver.service;

import com.example.springsilver.models.Users;
import com.example.springsilver.models.enumeration.UserRoles;

public interface UserService {

    Users register(String username, String first_name, String last_name,String email, String password, String repeatPassword);
    Users registerWithRole(String username, String first_name, String last_name,String email, String password, String repeatPassword, UserRoles role);
}
