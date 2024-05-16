package com.example.springsilver.service.impl;

import com.example.springsilver.config.Emailer;
import com.example.springsilver.models.Users;
import com.example.springsilver.models.enumeration.UserRoles;
import com.example.springsilver.models.exceptions.PasswordsDoNotMatchException;
import com.example.springsilver.models.exceptions.UserIdNotFoundException;
import com.example.springsilver.models.exceptions.UserNameAlreadyExistsException;
import com.example.springsilver.models.exceptions.UserNotActiveException;
import com.example.springsilver.repository.UserRepository;
import com.example.springsilver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
            String encodedPassword = passwordEncoder.encode(password);
            Users user=new Users(username, UserRoles.USER.ordinal() + 1, encodedPassword, email, null, last_name, first_name, image_pathurl, false);
            return userRepository.save(user);
        }
    }

    @Override
    public void forgotPassword(Long userId, String password) {

        Users user = userRepository.findUsersById(userId).orElseThrow(UserIdNotFoundException::new);
        String encodedPassword = passwordEncoder.encode(password);

        user.setPassword(encodedPassword);

        userRepository.save(user);
    }

    @Override
    public Users verifyUser(Long id) {

        Users verifyUser = userRepository.findUsersById(id).orElseThrow(UserIdNotFoundException::new);

        verifyUser.setActive(true);

        return userRepository.save(verifyUser);
    }

    public Users authenticateUser(String usernameOrEmail, String password) {
        Optional<Users> user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);

        if (user.isPresent()) {
            Users foundUser = user.get();
            if (foundUser.isActive()) {
                if (passwordEncoder.matches(password, foundUser.getPassword())) {
                    return foundUser;
                } else {
                    return null;
                }
            }
            else{
                throw new UserNotActiveException();
            }
        } else {
            throw new UserIdNotFoundException();
        }
    }

    @Override
    public Users getUserById(Long id) {
        return userRepository.findUsersById(id).orElseThrow(UserIdNotFoundException::new);
    }
}
