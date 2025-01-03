package com.example.Clique.dto;

import java.time.LocalDateTime;

public class PostSearchDTO {

    private Long postId;
    private String postText;
    private LocalDateTime postedTime;
    private String username;
    private Long likes;
    
    public PostSearchDTO(Long postId, String postText, LocalDateTime postedTime, String username, Long likes) {
        this.postId = postId;
        this.postText = postText;
        this.postedTime = postedTime;
        this.username = username;
        this.likes = likes;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public LocalDateTime getPostedTime() {
        return postedTime;
    }

    public void setPostedTime(LocalDateTime postedTime) {
        this.postedTime = postedTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getLikes() {
        return likes;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }

    
}
