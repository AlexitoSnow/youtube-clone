package com.globant.youtube_clone.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Document(value = "Video") @Data @AllArgsConstructor
public class Video {
    @Id
    private String id;
    private String title;
    private String description;
    private String userId;
    private AtomicInteger likes;
    private AtomicInteger dislikes;
    private Set<String> tags;
    private String videoUrl;
    private VideoStatus videoStatus;
    private AtomicInteger views;
    private LocalDate uploadedAt;
    private String thumbnailUrl;
    private List<Comment> comments;

    public Video() {
        likes = new AtomicInteger(0);
        dislikes = new AtomicInteger(0);
        views = new AtomicInteger(0);
        comments = new CopyOnWriteArrayList<>();
    }

    public void incrementViews() {
        views.incrementAndGet();
    }

    public void incrementLikes() {
        likes.incrementAndGet();
    }

    public void decrementLikes() {
        likes.decrementAndGet();
    }

    public void incrementDisLikes() {
        dislikes.incrementAndGet();
    }

    public void decrementDisLikes() {
        dislikes.decrementAndGet();
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }
}
