package com.globant.youtube_clone.mappers;

import com.globant.youtube_clone.dto.UserDisplay;
import com.globant.youtube_clone.dto.UserInfoDTO;
import com.globant.youtube_clone.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "name", target = "firstName")
    User fromDTO(UserInfoDTO dto);

    @Mapping(target = "name", source = "firstName")
    UserInfoDTO toDTO(User user);

    @Mapping(source = "subscribers", target = "subscribers", qualifiedByName = "subscribersCount")
    UserDisplay toDisplay(User user);

    @Named("subscribersCount")
    default Integer subscribersCount(Set<String> subscribers) {
        return subscribers.size();
    }

}
