package com.example.questionapp.security;

import com.example.questionapp.services.UserDetailsServiceImplementation;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

/**
 * JWT authentication filter that runs on every HTTP request
 * Checks whether the request contains a valid JWT token and authenticates the user
 * Extends OncePerRequestFilter to ensure the filter runs only once per request
 */
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    JWTTokenProvider jwtTokenProvider;

    @Autowired
    UserDetailsServiceImplementation userDetailsServiceImplementation;

    /**
     * Main filter method that runs on every HTTP request
     * Extracts and validates the JWT token, authenticates the user if valid
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try{
            // Extract the JWT token from the HTTP request
            String jwtToken = extractJwtFromRequest(request);
            // Check that the token is present and valid
            if(StringUtils.hasText(jwtToken) && jwtTokenProvider.validateToken(jwtToken)) {
                // Extract the user ID from the token
                Long id = jwtTokenProvider.getUserIdFromJWT(jwtToken);
                // Load user details by ID
                UserDetails user = userDetailsServiceImplementation.loadUserById(id);
                if(user != null){
                    // Create the authentication token
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    // Authenticate the user in the SecurityContext - from here on the user is available throughout the application
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        }catch(Exception e) {
            return;
        }
        // Continue the filter chain
        filterChain.doFilter(request, response);
    }

    /**
     * Extracts the JWT token from the Authorization header of the HTTP request
     * @param request The HTTP request
     * @return The JWT token without the "Bearer " prefix, or null if not present
     */
    private String extractJwtFromRequest(HttpServletRequest request) {
        // Read the Authorization header from the request
        String bearer = request.getHeader("Authorization");
        // Check whether the header is present and starts with "Bearer "
        if(StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")){
            // Remove the "Bearer " prefix and return only the token
            return bearer.substring("Bearer".length()+1);
        }
        return null;
    }

}
