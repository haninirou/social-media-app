package com.example.questionapp.controllers;

import com.example.questionapp.entities.Post;
import com.example.questionapp.requests.CreatePostRequest;
import com.example.questionapp.requests.UpdatePostRequest;
import com.example.questionapp.responses.PostResponse;
import com.example.questionapp.services.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for post management
 * Manages all HTTP endpoints for CRUD operations on posts
 */
@RestController
@RequestMapping("/posts")
public class PostController {
    private PostService postService;

    /**
     * Constructor for dependency injection
     * @param postService Service for post business logic
     */
    public PostController(PostService postService) {
        this.postService = postService;
    }

    /**
     * Retrieves all posts, optionally filtered by user ID
     * @param userId Optional user ID as a query parameter (posts?userId=123)
     * @return List of all posts or posts of a specific user
     */
    @GetMapping  // RequestParam => posts?userId=userId - reads the parameter from the URL query
    public List<PostResponse> getAllPosts(@RequestParam Optional<Long> userId) {
        // If no userId is given, all posts are returned
        return postService.getAllPosts(userId);
    }

    /**
     * Creates a new post
     * @param newPostRequest Request object with the post data
     * @return The created post
     */
    @PostMapping
    public Post createPost(@RequestBody CreatePostRequest newPostRequest) {
        return postService.createPost(newPostRequest);
    }

    /*
     * Alternative implementation for simple post return
     * @GetMapping("/{postId}")
     * public Post getPostById(@PathVariable Long postId) {
     *     return postService.getPostById(postId);
     * }
     */

    /**
     * Retrieves a post by ID, including likes
     * @param postId The ID of the requested post (from the URL path: posts/123)
     * @return Post response with details and likes
     */
    @GetMapping("/{postId}")  // PathVariable => posts/postId - reads the ID directly from the URL path
    public PostResponse getPostById(@PathVariable Long postId) {
        return postService.getPostByIdWithLikes(postId);
    }

    /**
     * Updates an existing post
     * @param postId The ID of the post to update
     * @param updatePostRequest Request object with the new post data
     * @return The updated post
     */
    @PutMapping("/{postId}")
    public Post updatePostById(@PathVariable Long postId, @RequestBody UpdatePostRequest updatePostRequest){
        return postService.updatePostById(postId, updatePostRequest);
    }

    /**
     * Deletes a post by ID
     * @param postId The ID of the post to delete
     */
    @DeleteMapping("/{postId}")
    public void deletePostById(@PathVariable Long postId){
        postService.deletePostById(postId);
    }

}
