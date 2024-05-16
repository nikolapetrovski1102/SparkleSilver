package com.example.springsilver.service.impl;

import com.example.springsilver.models.Users;
import com.example.springsilver.models.enumeration.UserRoles;
import com.example.springsilver.models.exceptions.InvalidUserCredentialsException;
import com.example.springsilver.models.exceptions.PasswordsDoNotMatchException;
import com.example.springsilver.models.exceptions.UserNameAlreadyExistsException;
import com.example.springsilver.repository.UserRepository;
import com.example.springsilver.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Users register(String username, String first_name, String last_name, String email, String password, String repeatPassword, String image_pathurl) {
        if(!password.equals(repeatPassword))
        {
            throw new PasswordsDoNotMatchException();
        }
        if(this.userRepository.findByUsername(username).isPresent())
        {
            throw new UserNameAlreadyExistsException(username);
        }
        else{
            Users user=new Users(username, UserRoles.USER.ordinal() + 1, password, email, null, last_name, first_name, image_pathurl, false);
            return userRepository.save(user);
        }
    }

    @Override
    public void forgotPassword(Long userId, String password) {

    }

    @Override
    public boolean authenticateUser(String usernameOrEmail, String password) {
        return false;
    }
}
