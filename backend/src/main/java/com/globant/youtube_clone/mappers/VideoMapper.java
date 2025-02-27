package com.globant.youtube_clone.mappers;

import com.globant.youtube_clone.dto.VideoDTO;
import com.globant.youtube_clone.model.Video;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface VideoMapper {
    void updateFromDTO(VideoDTO dto, @MappingTarget Video video);

    VideoDTO toDTO(Video save);
}
