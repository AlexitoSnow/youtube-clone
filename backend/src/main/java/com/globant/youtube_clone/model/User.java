package com.globant.youtube_clone.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Document(value = "User") @Data @AllArgsConstructor
public class User {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String sub;
    private String picture;
    private Set<String> subscribedToUsers;
    private Set<String> subscribers;
    private Set<String> videoHistory;
    private Set<String> likedVideos;
    private Set<String> unlikedVideos;

    public User() {
        subscribers = ConcurrentHashMap.newKeySet();
        subscribedToUsers = ConcurrentHashMap.newKeySet();
        videoHistory = ConcurrentHashMap.newKeySet();
        likedVideos = ConcurrentHashMap.newKeySet();
        unlikedVideos = ConcurrentHashMap.newKeySet();
    }

    public void addLikedVideo(String videoId) {
        likedVideos.add(videoId);
    }

    public void removeFromLikedVideo(String videoId) {
        likedVideos.remove(videoId);
    }

    public void addUnLikedVideo(String videoId) {
        unlikedVideos.add(videoId);
    }

    public void removeFromUnlikedVideo(String videoId) {
        unlikedVideos.remove(videoId);
    }

    public void addToHistory(String videoId) {
        videoHistory.add(videoId);
    }

    public void subscribeTo(String userId) {
        subscribedToUsers.add(userId);
    }

    public void addSubscriber(String userId) {
        subscribers.add(userId);
    }

    public void unsubscribeFrom(String userId) {
        subscribedToUsers.remove(userId);
    }

    public void removeSubscriber(String userId) {
        subscribers.remove(userId);
    }
}
