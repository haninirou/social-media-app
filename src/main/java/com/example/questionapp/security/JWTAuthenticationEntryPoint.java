package com.example.questionapp.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.IOException;

/**
 * JWT authentication entry point for handling unauthorized requests
 * Implements AuthenticationEntryPoint to send 401 errors on invalid authentication
 */
@Component  // Allows Spring to inject this class as a bean
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * Called when an unauthorized request is received
     * Sends back a 401 Unauthorized HTTP response
     * @param request The HTTP request
     * @param response The HTTP response 
     * @param authException The authentication exception
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // Send back a 401 Unauthorized error with the exception message
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
    }
}
