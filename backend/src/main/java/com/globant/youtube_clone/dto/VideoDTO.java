package com.globant.youtube_clone.dto;

import com.globant.youtube_clone.model.VideoStatus;

import java.util.Set;

public record VideoDTO (
        String id,
        String title,
        String description,
        String userId,
        Set<String>tags,
        String videoUrl,
        VideoStatus videoStatus,
        String thumbnailUrl
) {
}
