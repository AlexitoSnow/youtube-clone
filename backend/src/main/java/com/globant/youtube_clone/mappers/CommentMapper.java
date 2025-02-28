package com.globant.youtube_clone.mappers;

import com.globant.youtube_clone.dto.CommentDTO;
import com.globant.youtube_clone.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.concurrent.atomic.AtomicInteger;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "dislikes", source = "dislikes", qualifiedByName = "mapToInteger")
    @Mapping(target = "likes", source = "likes", qualifiedByName = "mapToInteger")
    CommentDTO toDTO(Comment save);

    @Named("mapToInteger")
    default Integer mapToInteger(AtomicInteger atomic) {
        return atomic.get();
    }

    @Named("mapToAtomic")
    default AtomicInteger mapToAtomic(Integer integer) {
        return new AtomicInteger(integer);
    }

    @Mapping(target = "dislikes", source = "dislikes", qualifiedByName = "mapToAtomic")
    @Mapping(target = "likes", source = "likes", qualifiedByName = "mapToAtomic")
    Comment fromDTO(CommentDTO commentDTO);
}
