package com.globant.youtube_clone.repository;

import com.globant.youtube_clone.model.Video;
import com.globant.youtube_clone.model.VideoStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends MongoRepository<Video, String> {
    List<Video> findByVideoStatus(VideoStatus videoStatus);
}
