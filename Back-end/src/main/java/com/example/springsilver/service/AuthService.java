package com.example.springsilver.service;

import com.example.springsilver.models.Users;
import org.springframework.stereotype.Service;

public interface AuthService {

    Users login(String username,String password);
}
