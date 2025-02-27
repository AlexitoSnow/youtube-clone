package com.globant.youtube_clone.service;

import com.globant.youtube_clone.dto.VideoDTO;
import com.globant.youtube_clone.mappers.VideoMapper;
import com.globant.youtube_clone.model.Video;
import com.globant.youtube_clone.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service @RequiredArgsConstructor
public class VideoService {
    private final S3Service s3Service;
    private final VideoRepository videoRepository;
    private final VideoMapper mapper;

    private String extractId(String url) {
        return url.split("/")[3];
    }

    public VideoDTO uploadVideo(MultipartFile file) {
        String videoUrl = s3Service.uploadVideo(file);
        var video = new Video();
        video.setVideoUrl(videoUrl);
        video.setId(extractId(videoUrl));
        return mapper.toDTO(videoRepository.save(video));
    }

    public VideoDTO uploadThumbnail(MultipartFile file, String videoId) {
        Video stored = getVideoById(videoId);
        String imageUrl = s3Service.uploadThumbnail(file, videoId);
        stored.setThumbnailUrl(imageUrl);
        return mapper.toDTO(videoRepository.save(stored));
    }

    public VideoDTO editVideo(VideoDTO video) {
        Video stored = getVideoById(video.id());
        mapper.updateFromDTO(video, stored);
        videoRepository.save(stored);
        return video;
    }

    private Video getVideoById(String videoId) {
        return videoRepository.findById(
                        videoId)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find video by the id - " + videoId));
    }

    public VideoDTO getVideoDetails(String videoId) {
        return mapper.toDTO(getVideoById(videoId));
    }

    public List<VideoDTO> getVideos() {
        return videoRepository.findAll().stream().map(mapper::toDTO).toList();
    }
}
