package com.example.springsilver.service;

import com.example.springsilver.models.Users;

public interface AuthService {

    Users login(String username,String password);
}
