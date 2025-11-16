package com.example.questionapp.responses;

import com.example.questionapp.entities.Like;
import lombok.Data;

@Data
public class LikeResponse {  // Instead of returning User and Post completely, we only return their IDs, which is why we created LikeResponse.

    private Long id;
    private Long userId;
    private Long postId;

    public LikeResponse(Like like) {
        this.id = like.getId();
        this.userId = like.getUser().getId();
        this.postId = like.getPost().getId();
    }
}