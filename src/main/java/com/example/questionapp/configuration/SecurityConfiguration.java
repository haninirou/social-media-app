package com.example.questionapp.configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import com.example.questionapp.security.JWTAuthenticationEntryPoint;
import com.example.questionapp.security.JWTAuthenticationFilter;
import com.example.questionapp.services.UserDetailsServiceImplementation;

/**
 * Spring Security configuration class
 * Configures the security settings for the entire application
 * including JWT authentication, CORS policies and endpoint permissions
 * Enables automatic creation and configuration of all security components
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration{

    /** Service for user details and authentication */
    private UserDetailsServiceImplementation userDetailsService;

    /** Handler for authentication exceptions */
    private JWTAuthenticationEntryPoint handler;

    /**
     * Constructor for dependency injection
     * @param userDetailsService Service for loading user details
     * @param handler Entry point for handling authentication errors
     */
    public SecurityConfiguration(UserDetailsServiceImplementation userDetailsService, JWTAuthenticationEntryPoint handler) {
        this.userDetailsService = userDetailsService;
        this.handler = handler;
    }

    /**
     * Bean for the JWT authentication filter
     * @return New JWT authentication filter
     */
    @Bean
    public JWTAuthenticationFilter JWTAuthenticationFilter() {
        return new JWTAuthenticationFilter();
    }

    /**
     * Bean for password encryption
     * @return BCrypt password encoder for secure password hashes
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean for the authentication manager
     * @param authenticationConfiguration Spring Security configuration
     * @return Authentication manager for user login
     * @throws Exception On configuration errors
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Bean for the CORS filter (Cross-Origin Resource Sharing)
     * Allows requests from different domains and ports
     * @return Configured CORS filter for frontend integration
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        
        // CORS configuration for frontend integration
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");           // Allow all origins (for development)
        config.addAllowedHeader("*");           // Allow all headers
        config.addAllowedMethod("OPTIONS");     // Preflight requests
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    /**
     * Main configuration of the security filter chain
     * Defines which endpoints are publicly accessible and which require authentication
     * @param httpSecurity HTTP security configuration
     * @return Configured security filter chain
     * @throws Exception On configuration errors
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // Enable CORS support - uses the CorsFilter defined above
                .cors()
                .and()
                // Disable CSRF protection (for REST APIs with JWT and Postman tests)
                .csrf().disable()
                // Custom exception handler for authentication errors
                .exceptionHandling().authenticationEntryPoint(handler).and()
                // Stateless session management (no server-side session storage)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // Define the authorization rules
                .authorizeHttpRequests()
                    // Authentication endpoints are publicly accessible (no token required)
                    .requestMatchers("/auth/**").permitAll()
                    // GET requests for posts are public (read without login)
                    .requestMatchers(HttpMethod.GET, "/posts").permitAll()
                    // GET requests for comments are public
                    .requestMatchers(HttpMethod.GET, "/comments").permitAll()
                    // All other requests require authentication
                    .anyRequest().authenticated();

        // Add the JWT filter before the standard authentication filter
        httpSecurity.addFilterBefore(JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        
        return httpSecurity.build();
    }

}
