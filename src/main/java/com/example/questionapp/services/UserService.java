package com.example.questionapp.services;


import com.example.questionapp.dataAccess.CommentRepository;
import com.example.questionapp.dataAccess.LikeRepository;
import com.example.questionapp.dataAccess.PostRepository;
import com.example.questionapp.dataAccess.UserRepository;
import com.example.questionapp.entities.Comment;
import com.example.questionapp.entities.Like;
import com.example.questionapp.entities.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service for user management and user activities
 * Manages CRUD operations for users and their activities (posts, likes, comments)
 */
@Service
public class UserService {

    private UserRepository userRepository;
    private LikeRepository likeRepository;
    private CommentRepository commentRepository;
    private PostRepository postRepository;

    /**
     * Constructor for dependency injection of all required repositories
     * @param userRepository Repository for user operations
     * @param likeRepository Repository for like operations
     * @param commentRepository Repository for comment operations
     * @param postRepository Repository for post operations
     */
    public UserService(UserRepository userRepository, LikeRepository likeRepository, CommentRepository commentRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.likeRepository = likeRepository;
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }


    /**
     * Returns all users
     * @return List of all users
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Creates a new user
     * @param user The user to create
     * @return The saved user
     */
    public User createUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Finds a user by ID
     * @param userId The ID of the user being searched for
     * @return The user found, or null if not present
     */
    public User getUserById(Long userId) {
        // The user might not exist in the database - check required
        return userRepository.findById(userId).orElse(null);
    }

    /**
     * Updates an existing user
     * @param userId The ID of the user to update
     * @param newUser The user object with the new data
     * @return The updated user, or null if not found
     */
    public User updateUserById(Long userId, User newUser) {
        Optional<User> user = userRepository.findById(userId);
        // Check whether the user exists
        if (user.isPresent()){
            User foundUser = user.get();
            foundUser.setUsername(newUser.getUsername());
            foundUser.setPassword(newUser.getPassword());
            foundUser.setImage(newUser.getImage());
            userRepository.save(foundUser);
            return foundUser;
        }else
            return null;
    }

    /**
     * Deletes a user by ID
     * @param userId The ID of the user to delete
     */
    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }

    /**
     * Finds a user by username
     * @param username The username
     * @return The user found, or null
     */
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

   
}
