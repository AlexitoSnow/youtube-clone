package com.globant.youtube_clone.controller;

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

    @GetMapping
    public List<VideoDTO> retrieveVideos() {
        return service.getVideos();
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
}
