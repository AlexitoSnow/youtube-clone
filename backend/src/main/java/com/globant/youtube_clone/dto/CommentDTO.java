package com.globant.youtube_clone.dto;

public record CommentDTO(
        String text, String authorId, Integer likes, Integer dislikes
) {
}
