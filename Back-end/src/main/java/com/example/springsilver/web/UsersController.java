package com.example.springsilver.web;

import com.example.springsilver.config.CookieManager;
import com.example.springsilver.config.Emailer;
import com.example.springsilver.models.Users;
import com.example.springsilver.models.dto.UserRegisterDTO;
import com.example.springsilver.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3001", allowCredentials = "true")
public class UsersController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final Emailer emailer;
    private final CookieManager cookieManager;

    public UsersController(UserService userService, PasswordEncoder passwordEncoder, Emailer emailer, CookieManager cookieManager) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.emailer = emailer;
        this.cookieManager = cookieManager;
    }

    @PostMapping("/login")
    public ResponseEntity<Long> login (@RequestParam String usernameOrEmail, @RequestParam String password, HttpServletResponse resp, HttpServletRequest req){

        String isAuthorized = cookieManager.checkSession(req);

        if (isAuthorized.equals("Not authorized")) {
            Users user = userService.authenticateUser(usernameOrEmail, password);

            if (user != null) {
                cookieManager.setCookie(resp, user.getId().toString());
                return ResponseEntity.ok(user.getId());
            } else {
                return ResponseEntity.notFound().build();
            }
        }
        else{
            return ResponseEntity.badRequest().build();
        }

    }

    @PostMapping("/logout")
    public ResponseEntity<Users> logout (HttpServletResponse resp, HttpServletRequest req){

        String authorized = cookieManager.checkSession(req);

        if (authorized.equals("Not authorized")){
            return ResponseEntity.badRequest().build();
        }
        else{
            cookieManager.deleteCookie(resp);
            return ResponseEntity.ok().build();
        }

    }

    @PostMapping("/register")
    public ResponseEntity<?> register (@RequestBody UserRegisterDTO newUser, HttpServletRequest request){

        try{
            Users savedUser = userService.register(newUser.getUsername(), newUser.getFirstName(), newUser.getLastName(), newUser.getEmail(), newUser.getPassword(), newUser.getRepeatPassword(), newUser.getImagePathUrl());

            StringBuffer url = request.getRequestURL();
            String verificationLink = url.toString() + "/verify/" + savedUser.getId();

            String emailBody = "Please verify your profile by clicking the following <a href='" + verificationLink + "'>link</a>";

            emailer.sendEmail(newUser.getEmail(), "Please verify your profile", emailBody);

            return ResponseEntity.ok().build();

        }
        catch (Exception ex){
            System.out.println(ex.toString());
            return ResponseEntity.internalServerError().build();
        }

    }

    @GetMapping("register/verify/{userId}")
    public ResponseEntity<?> verifyUser (@PathVariable Long userId){

        try{
            userService.verifyUser(userId);
            return ResponseEntity.ok().build();
        }
        catch (Exception ex){
            return ResponseEntity.internalServerError().build();
        }

    }

    @PostMapping("/forgotPassword/{userId}")
    public ResponseEntity<?> forgotPassword (@PathVariable Long userId, @RequestBody String newPassword){

        try{
            userService.forgotPassword(userId, newPassword);
            return ResponseEntity.ok().build();
        }
        catch (Exception ex){
            return ResponseEntity.internalServerError().build();
        }

    }

    @GetMapping("/checkSession")
    public ResponseEntity<String> checkSession (HttpServletRequest req){

        String session = cookieManager.checkSession(req);

        if (session.equals("Not authorized")){
            return ResponseEntity.badRequest().build();
        }
        else{
            return ResponseEntity.ok(session);
        }

    }

}
