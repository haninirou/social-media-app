package com.example.questionapp.responses;


import com.example.questionapp.entities.Like;
import com.example.questionapp.entities.Post;
import lombok.Data;

import java.util.List;

/**
 * Response DTO for post data
 * This class is used to return only the desired post information
 * and to exclude sensitive data such as passwords
 * 
 * Example JSON: {"id": 102, "userId": 1, "username": "ysk", "text": "Post content", "title": "Post title"}
 */
@Data
public class PostResponse {

    /** Unique ID of the post */
    private Long id;
    
    /** ID of the user who created the post */
    private Long userId;
    
    /** Username of the post creator */
    private String username;
    
    /** Text content of the post */
    private String text;
    
    /** Title of the post */
    private String title;

    /** List of likes this post has received */
    private List<LikeResponse> postLikes;
    
    /**
     * Constructor for converting a post entity into a response DTO
     * Mapping of entity data to response objects
     * 
     * @param post The post entity from the database
     * @param likes List of like responses for this post
     */
    public PostResponse(Post post, List<LikeResponse> likes){
       this.id = post.getId();
       this.userId = post.getUser().getId();
       this.username = post.getUser().getUsername();
       this.title = post.getTitle();
       this.text = post.getText();
       this.postLikes = likes;
    }
}
