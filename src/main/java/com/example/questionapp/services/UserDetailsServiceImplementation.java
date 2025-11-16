package com.example.questionapp.services;


import com.example.questionapp.dataAccess.UserRepository;
import com.example.questionapp.entities.User;
import com.example.questionapp.security.JWTUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Implementation of UserDetailsService for Spring Security
 * Loads user details for authentication from the database
 */
@Service
public class UserDetailsServiceImplementation implements UserDetailsService {

    private UserRepository userRepository;

    /**
     * Constructor for dependency injection
     * @param userRepository Repository for user database operations
     */
    public UserDetailsServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Loads user details by username (standard Spring Security method)
     * @param username The username
     * @return UserDetails object of the user found
     * @throws UsernameNotFoundException if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        // Convert the User entity into a UserDetails object for Spring Security
        return JWTUserDetails.create(user);
    }

    /**
     * Loads user details by user ID (for JWT token validation)
     * @param id The user ID
     * @return UserDetails object of the user found
     */
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).get();
        return JWTUserDetails.create(user);
    }

}
