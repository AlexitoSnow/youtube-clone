package com.globant.youtube_clone.mappers;

import com.globant.youtube_clone.dto.UserInfoDTO;
import com.globant.youtube_clone.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "name", target = "firstName")
    User fromDTO(UserInfoDTO dto);
}
