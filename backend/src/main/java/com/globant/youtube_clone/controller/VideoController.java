package com.globant.youtube_clone.controller;

import com.globant.youtube_clone.dto.CommentDTO;
import com.globant.youtube_clone.dto.UploadResponse;
import com.globant.youtube_clone.dto.VideoDTO;
import com.globant.youtube_clone.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@RestController @RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideoController {
    private final VideoService service;

    @PostMapping("upload") @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UploadResponse> uploadVideo(@RequestParam MultipartFile file) {
        var response = service.uploadVideo(file);
        return ResponseEntity.created(URI.create(response.videoUrl())).body(new UploadResponse(response.id(), response.videoUrl()));
    }

    @GetMapping @ResponseStatus(HttpStatus.OK)
    public List<VideoDTO> retrievePublicVideos() {
        return service.getPublicVideos();
    }

    @PostMapping("upload/{videoId}/thumbnail") @ResponseStatus(HttpStatus.CREATED)
    public UploadResponse uploadThumbnail(@RequestParam MultipartFile file, @PathVariable String videoId) {
        var response = service.uploadThumbnail(file, videoId);
        return new UploadResponse(response.id(), response.thumbnailUrl());
    }

    @PutMapping @ResponseStatus(HttpStatus.OK)
    public VideoDTO editVideoMetadata(@RequestBody VideoDTO video) {
        return service.editVideo(video);
    }

    @GetMapping("{videoId}") @ResponseStatus(HttpStatus.OK)
    public VideoDTO getVideoDetails(@PathVariable String videoId) {
        return service.getVideoDetails(videoId);
    }

    @PutMapping("{videoId}/like") @ResponseStatus(HttpStatus.OK)
    public VideoDTO likeVideo(@PathVariable String videoId) {
        return service.likeVideo(videoId);
    }

    @PutMapping("{videoId}/dislike") @ResponseStatus(HttpStatus.OK)
    public VideoDTO dislikeVideo(@PathVariable String videoId) {
        return service.dislikeVideo(videoId);
    }

    @PostMapping("{videoId}/comments") @ResponseStatus(HttpStatus.OK)
    public void addComment(@PathVariable String videoId, @RequestBody CommentDTO commentDTO) {
        service.addComment(videoId, commentDTO);
    }

    @GetMapping("{videoId}/comments") @ResponseStatus(HttpStatus.OK)
    public List<CommentDTO> getCommentsFrom(@PathVariable String videoId) {
        return service.getAllCommentsFrom(videoId);
    }

}
