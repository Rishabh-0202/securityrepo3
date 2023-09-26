package com.example.ss_2023_c4.config.filter;

import com.example.ss_2023_c4.config.authentication.ApiKeyAuthentication;
import com.example.ss_2023_c4.config.managers.CustomAuthenticationManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@AllArgsConstructor
public class ApiKeyFilter extends OncePerRequestFilter {

    //private CustomAuthenticationManager customAuthenticationManager;
    private final String key;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        CustomAuthenticationManager manager = new CustomAuthenticationManager(key);

        var headerKey = request.getHeader("x-api-key");

        if(headerKey.equals("null") || request==null){
            filterChain.doFilter(request,response);
        }

        var auth = new ApiKeyAuthentication(headerKey);
        try{
        var a= manager.authenticate(auth);
        if(a.isAuthenticated()){
            SecurityContextHolder.getContext().setAuthentication(a);
            filterChain.doFilter(request,response);
        }
        else{
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    } catch (AuthenticationException e){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

    }
}
