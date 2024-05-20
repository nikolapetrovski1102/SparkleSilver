package com.example.springsilver;

import com.example.springsilver.models.Users;
import com.example.springsilver.models.enumeration.UserRoles;
import com.example.springsilver.models.exceptions.PasswordsDoNotMatchException;
import com.example.springsilver.models.exceptions.UserNameAlreadyExistsException;
import com.example.springsilver.repository.UserRepository;
import com.example.springsilver.service.impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.management.relation.Role;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class UserRegistrationTest {

    @Mock
    private  UserRepository userRepository;

    @Mock
    private  PasswordEncoder passwordEncoder;

    private UserServiceImpl service;

    @Before
    public void init()
    {
        MockitoAnnotations.initMocks(this);
        Users user=new Users("username", 1,"password","user@gmail.com", "user", "user",  "img.com", true);
        Mockito.when(this.userRepository.save(Mockito.any(Users.class))).thenReturn(user);
        Mockito.when(this.passwordEncoder.encode(Mockito.anyString())).thenReturn("password");

        service=Mockito.spy(new UserServiceImpl(this.userRepository,this.passwordEncoder));
    }

    @Test
    public void testSuccessRegister(){
        Users user=this.service.register("username","user","user","user@gmail.com","password","password","img.com");

        Mockito.verify(this.service).register("username","user","user","user@gmail.com","password","password","img.com");

        Assert.assertNotNull("User is null",user);

        Assert.assertEquals("name do not match","user",user.getFirst_name());
        Assert.assertEquals("password do not match","password",user.getPassword());
        Assert.assertEquals("username do not match","username",user.getUsername());
        Assert.assertEquals("email do not match","user@gmail.com",user.getEmail());


    }



    @Test
    public void testDuplicateUsername(){
        Users user = new Users("username", 1, "password", "user@gmail.com", "user", "user", "img.com", true);

        String username = "username";


        Mockito.doThrow(new UserNameAlreadyExistsException(username))
                .when(service).register(username, "user", "user", "user@gmail.com", "password", "password", "img.com");


        UserNameAlreadyExistsException thrown = Assert.assertThrows(
                "UserNameAlreadyExistsException expected",
                UserNameAlreadyExistsException.class,
                () -> this.service.register(username, "user", "user", "user@gmail.com", "password", "password", "img.com")
        );


        Assert.assertEquals(String.format("User with username: username already exists"), thrown.getMessage());


        Mockito.verify(service).register(username, "user", "user", "user@gmail.com", "password", "password", "img.com");
    }


    @Test
    public void testPasswordMismatch() {
        String username = "username";
        String password = "password";
        String repeatPassword = "otherPassword";


        Mockito.doThrow(new PasswordsDoNotMatchException())
                .when(service).register(username, "user", "user", "user@gmail.com", password, repeatPassword, "img.com");


        PasswordsDoNotMatchException thrown = Assert.assertThrows(
                "PasswordsDoNotMatchException expected",
                PasswordsDoNotMatchException.class,
                () -> this.service.register(username, "user", "user", "user@gmail.com", password, repeatPassword, "img.com")
        );


        Assert.assertEquals("Passwords do not match", thrown.getMessage());


        Mockito.verify(service).register(username, "user", "user", "user@gmail.com", password, repeatPassword, "img.com");
    }

}
