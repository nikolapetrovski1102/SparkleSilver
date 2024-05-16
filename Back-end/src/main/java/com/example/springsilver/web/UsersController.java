package com.example.springsilver.web;

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
public class UsersController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final Emailer emailer;

    public UsersController(UserService userService, PasswordEncoder passwordEncoder, Emailer emailer) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.emailer = emailer;
    }

    @PostMapping("/login")
    public ResponseEntity<Users> login (@RequestParam String usernameOrEmail, @RequestParam String password, HttpServletResponse resp, HttpServletRequest req){

        String isAuthorized = checkSession(req);

        if (isAuthorized.isEmpty()) {
            Users user = userService.authenticateUser(usernameOrEmail, password);

            if (user != null) {
                setCookie(resp, user.getId().toString());
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.badRequest().build();
            }
        }
        else{
            return ResponseEntity.badRequest().build();
        }

    }

    @PostMapping("/logout")
    public ResponseEntity<Users> logout (HttpServletResponse resp, HttpServletRequest req){

        String authorized = checkSession(req);

        if (authorized.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        else{
            deleteCookie(resp);
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

    @GetMapping("/setCookie")
    public void setCookie(HttpServletResponse response, String cookieValue) {
        Cookie cookie = new Cookie("session", cookieValue);
        cookie.setMaxAge(24 * 60 * 60);
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        try{
            response.addCookie(cookie);
        }
        catch (Exception ex){
            System.out.println(ex.toString());
        }
    }

    @GetMapping("/checkSession")
    public String checkSession(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("session")) {
                    return cookie.getValue();
                }
            }
        }
        return "";
    }

    @GetMapping("/delete")
    public void deleteCookie (HttpServletResponse response){
        Cookie cookie = new Cookie("user", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setHttpOnly(true);

        response.addCookie(cookie);
    }

}
