package com.globant.youtube_clone.service;

import com.globant.youtube_clone.dto.CommentDTO;
import com.globant.youtube_clone.dto.VideoDTO;
import com.globant.youtube_clone.mappers.CommentMapper;
import com.globant.youtube_clone.mappers.VideoMapper;
import com.globant.youtube_clone.model.Comment;
import com.globant.youtube_clone.model.Video;
import com.globant.youtube_clone.model.VideoStatus;
import com.globant.youtube_clone.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Service @RequiredArgsConstructor @Slf4j
public class VideoService {
    private final S3Service s3Service;
    private final VideoRepository videoRepository;
    private final VideoMapper videoMapper;
    private final CommentMapper commentMapper;
    private final UserService userService;

    private String extractId(String url) {
        return url.split("/")[3];
    }

    public VideoDTO uploadVideo(MultipartFile file) {
        String videoUrl = s3Service.uploadVideo(file);
        var video = new Video();
        video.setVideoUrl(videoUrl);
        video.setId(extractId(videoUrl));
        return videoMapper.toDTO(videoRepository.save(video));
    }

    public VideoDTO uploadThumbnail(MultipartFile file, String videoId) {
        Video stored = getVideoById(videoId);
        String imageUrl = s3Service.uploadThumbnail(file, videoId);
        stored.setThumbnailUrl(imageUrl);
        return videoMapper.toDTO(videoRepository.save(stored));
    }

    public VideoDTO editVideo(VideoDTO video) {
        Video stored = getVideoById(video.id());
        videoMapper.updateFromDTO(video, stored);
        stored.setUploadedAt(LocalDate.now());
        stored.setUserId(userService.getCurrent().getId());
        videoRepository.save(stored);
        return video;
    }

    private Video getVideoById(String videoId) {
        return videoRepository.findById(
                        videoId)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find video by the id - " + videoId));
    }

    public VideoDTO getVideoDetails(String videoId) {
        var video = getVideoById(videoId);
        video.incrementViews();
        try {
            userService.addToWatchHistory(videoId);
        } catch (Exception e) {
            log.warn("Video {} watched but not user logged", videoId);
        }
        return videoMapper.toDTO(videoRepository.save(video));
    }

    public List<VideoDTO> getVideos() {
        return videoRepository.findAll().stream().map(videoMapper::toDTO).toList();
    }

    public List<VideoDTO> getPublicVideos() {
        var response = videoRepository.findByVideoStatus(VideoStatus.PUBLIC).stream().map(videoMapper::toDTO).toList();
        log.info("Videos response: {}", response.size());
        return response;
    }

    public VideoDTO likeVideo(String videoId) {
        var video = getVideoById(videoId);
        if (userService.ifLikedVideo(videoId)) {
            video.decrementLikes();
            userService.removeFromLikedVideos(videoId);
        } else if (userService.ifUnlikedVideo(videoId)) {
            video.incrementLikes();
            video.decrementDisLikes();
            userService.addToLikedVideos(videoId);
            userService.removeFromUnlikedVideos(videoId);
        } else {
            video.incrementLikes();
            userService.addToLikedVideos(videoId);
        }
        videoRepository.save(video);
        return videoMapper.toDTO(video);
    }

    public VideoDTO dislikeVideo(String videoId) {
        var video = getVideoById(videoId);
        if (userService.ifUnlikedVideo(videoId)) {
            video.decrementDisLikes();
            userService.removeFromUnlikedVideos(videoId);
        } else if (userService.ifLikedVideo(videoId)) {
            video.incrementDisLikes();
            video.decrementLikes();
            userService.addToUnlikedVideos(videoId);
            userService.removeFromLikedVideos(videoId);
        } else {
            video.incrementDisLikes();
            userService.addToUnlikedVideos(videoId);
        }
        videoRepository.save(video);
        return videoMapper.toDTO(video);
    }

    public void addComment(String videoId, CommentDTO commentDTO) {
        var video = getVideoById(videoId);
        Comment comment = commentMapper.fromDTO(commentDTO);
        video.addComment(comment);
        videoRepository.save(video);
    }

    public List<CommentDTO> getAllCommentsFrom(String videoId) {
        var video = getVideoById(videoId);
        return video.getComments().stream().map(commentMapper::toDTO).toList();
    }
}
