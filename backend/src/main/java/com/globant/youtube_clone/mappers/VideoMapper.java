package com.globant.youtube_clone.mappers;

import com.globant.youtube_clone.dto.VideoDTO;
import com.globant.youtube_clone.model.Video;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.concurrent.atomic.AtomicInteger;

@Mapper(componentModel = "spring")
public interface VideoMapper {

    @Mapping(target = "dislikes", source = "dislikes", qualifiedByName = "mapToAtomic")
    @Mapping(target = "likes", source = "likes", qualifiedByName = "mapToAtomic")
    @Mapping(target = "views", source = "views", qualifiedByName = "mapToAtomic")
    void updateFromDTO(VideoDTO dto, @MappingTarget Video video);

    @Mapping(target = "dislikes", source = "dislikes", qualifiedByName = "mapToInteger")
    @Mapping(target = "likes", source = "likes", qualifiedByName = "mapToInteger")
    @Mapping(target = "views", source = "views", qualifiedByName = "mapToInteger")
    VideoDTO toDTO(Video save);

    @Named("mapToInteger")
    default Integer mapToInteger(AtomicInteger atomic) {
        return atomic.get();
    }

    @Named("mapToAtomic")
    default AtomicInteger mapToAtomic(Integer integer) {
        return new AtomicInteger(integer);
    }
}
