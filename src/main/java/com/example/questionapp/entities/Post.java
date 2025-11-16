package com.example.questionapp.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

/**
 * Post entity for representing posts in the social media application
 * Represents a post by a user with title, text and creation date
 */
@Entity
@Data
@Table(name = "post")
public class Post {
    
    /** Unique ID of the post */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /** 
     * User who created this post
     * Many-to-one relationship: one user can have many posts
     * Cascade: if the user is deleted, their posts are deleted too
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    /** Title of the post */
    private String title;

    /** 
     * Text content of the post
     * @Lob for large amounts of text, stored as TEXT type in the database
     */
    @Lob
    @Column(columnDefinition = "text")
    private String text;

    /** 
     * Creation date of the post
     * Set automatically on save with a timestamp
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;


}
