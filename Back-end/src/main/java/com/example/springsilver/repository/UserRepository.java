package com.example.springsilver.repository;

import com.example.springsilver.models.Users;
import com.fasterxml.jackson.annotation.OptBoolean;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository <Users,String>{
    Optional<Users> findByFirst_nameAndPassword(String first_name,String password);
    Optional<User> findByFirst_name(String first_name);
}
