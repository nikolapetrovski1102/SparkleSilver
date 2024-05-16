package com.example.springsilver.config;

import com.example.springsilver.models.Users;
import com.example.springsilver.models.enumeration.UserRoles;
import com.example.springsilver.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void initData(){
        if (userRepository.findAll().isEmpty()){
            String encodedPassword = passwordEncoder.encode("admin");
            Users u1 = new Users("admin", UserRoles.ADMIN.ordinal() + 1, encodedPassword, "nikpetrovski007@gmail.com", "Admin", "Admin", "", true);

            userRepository.save(u1);
        }

    }

}
