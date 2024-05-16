package com.example.springsilver.config;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public class CookieManager {

    public void setCookie(HttpServletResponse response, String cookieValue) {
        Cookie cookie = new Cookie("session", cookieValue);
        cookie.setMaxAge(24 * 60 * 60);
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setHttpOnly(false);
        try{
            response.addCookie(cookie);
        }
        catch (Exception ex){
            System.out.println(ex.toString());
        }
    }

    public String checkSession (HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("session")) {
                    return cookie.getValue();
                }
            }
        }
        return "Not authorized";
    }

    public void deleteCookie (HttpServletResponse response){
        Cookie cookie = new Cookie("session", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setHttpOnly(false);

        response.addCookie(cookie);
    }

}
