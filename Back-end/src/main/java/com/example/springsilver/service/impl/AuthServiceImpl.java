package com.example.springsilver.service.impl;


import com.example.springsilver.models.Users;
import com.example.springsilver.models.exceptions.InvalidArgumentsException;
import com.example.springsilver.models.exceptions.InvalidUserCredentialsException;
import com.example.springsilver.repository.UserRepository;
import com.example.springsilver.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Users login(String username, String password) {
        if(username==null || username.isEmpty() || password==null || password.isEmpty())
        {
            throw new InvalidArgumentsException();
        }
        return userRepository.findByUsernameAndPassword(username,password).orElseThrow(InvalidUserCredentialsException::new);
    }
}
