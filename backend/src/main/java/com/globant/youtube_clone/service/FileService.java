package com.globant.youtube_clone.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String uploadVideo(MultipartFile file);

    String uploadThumbnail(MultipartFile file, String id);
}
