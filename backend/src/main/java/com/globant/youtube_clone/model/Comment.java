package com.globant.youtube_clone.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.concurrent.atomic.AtomicInteger;

@Data @NoArgsConstructor @AllArgsConstructor
public class Comment {
    @Id
    private String id;
    private String text;
    private String authorId;
    private AtomicInteger likes;
    private AtomicInteger dislikes;

    public void incrementLike() {
        likes.incrementAndGet();
    }

    public void decrementLike() {
        likes.decrementAndGet();
    }

    public void incrementDislike() {
        dislikes.incrementAndGet();
    }

    public void decrementDislike() {
        dislikes.decrementAndGet();
    }
}
