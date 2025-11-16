package com.example.questionapp.security;

import com.example.questionapp.entities.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * JWT user details - implements UserDetails for Spring Security
 * Spring Security requires a UserDetails object for authentication processes
 * This class is used for JWT-based authentication
 */
@Getter
@Setter
@Data
public class JWTUserDetails implements UserDetails {

    public Long id;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    /**
     * Private constructor for JWTUserDetails
     * @param id User ID
     * @param username Username
     * @param password Password
     * @param authorities User authorities
     */
    private JWTUserDetails(Long id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    /**
     * Factory method for creating JWTUserDetails from a User object
     * @param user The user entity object
     * @return A UserDetails object for Spring Security
     */
    public static JWTUserDetails create(User user) {
        List<GrantedAuthority> authoritiesList = new ArrayList<>();
        // Assign the default authority "user" (no admin system implemented)
        authoritiesList.add(new SimpleGrantedAuthority("user"));
        return new JWTUserDetails(user.getId(), user.getUsername(), user.getPassword(), authoritiesList);
    }

    /** @return true - account is not expired */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /** @return true - account is not locked */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /** @return true - credentials are not expired */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /** @return true - account is enabled */
    @Override
    public boolean isEnabled() {
        return true;
    }

}
