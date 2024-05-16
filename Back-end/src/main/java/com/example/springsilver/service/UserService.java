package com.example.springsilver.service;

import com.example.springsilver.models.Users;
import com.example.springsilver.models.enumeration.UserRoles;
import org.springframework.stereotype.Service;

public interface UserService {

    Users register(String username, String first_name, String last_name,String email, String password, String repeatPassword, String image_pathurl);
    void forgotPassword(Long userId, String password);
    Users authenticateUser(String usernameOrEmail, String password);
    Users getUserById (Long id);
    Users verifyUser (Long id);

}
