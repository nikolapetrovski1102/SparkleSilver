package com.example.springsilver.repository;

import com.example.springsilver.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository <Users, Long>{
    Optional<Users> findByUsernameAndPassword(String username,String password);
    Optional<Users> findByUsername(String username);
    Optional<Users> findUsersById (Long id);

}
