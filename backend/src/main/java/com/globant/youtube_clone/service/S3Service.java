package com.globant.youtube_clone.service;

import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;

import java.io.IOException;
import java.util.UUID;

@Service @RequiredArgsConstructor
public class S3Service implements FileService {
    private final S3Client s3Client;
    private final S3Template s3Template;
    @Value("${s3.bucket.name}")
    private final String bucketName;

    @Override
    public String uploadVideo(MultipartFile file) {
        return uploadFile(file, "video", UUID.randomUUID().toString());
    }

    private String uploadFile(MultipartFile file, String type, String id) {
        String fileNameExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        String key = id + "/" + type + "." + fileNameExtension;
        var metadata = ObjectMetadata.builder()
                .contentLength(file.getSize())
                .contentType(file.getContentType())
                .acl(ObjectCannedACL.PUBLIC_READ)
                .build();
        try {
            S3Resource resource = s3Template.upload(bucketName, key, file.getInputStream(), metadata);
            return resource.getURL().toString();
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Unable to upload the file");
        }
    }

    @Override
    public String uploadThumbnail(MultipartFile file, String id) {
        return uploadFile(file, "image", id);
    }
}
