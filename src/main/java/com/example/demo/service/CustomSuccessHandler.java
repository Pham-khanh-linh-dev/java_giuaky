package com.example.demo.service;

import com.example.demo.model.CustomUserDetail;
import com.example.demo.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CustomSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        var authorities = authentication.getAuthorities();
        var roles = authorities.stream().map(r -> r.getAuthority()).findFirst();

        // Get the user from CustomUserDetail
        CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();
        User user = userDetails.getUser();

        // Store user in session
        HttpSession session = request.getSession();
        session.setAttribute("user", user);

        if(roles.orElse("").equals("ADMIN")){
            response.sendRedirect("/admin-page");
        } else if (roles.orElse("").equals("USER")) {
            response.sendRedirect("/dashboard");
        } else {
            response.sendRedirect("/error");
        }
    }
}
