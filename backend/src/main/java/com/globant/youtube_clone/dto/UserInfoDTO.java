package com.globant.youtube_clone.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserInfoDTO(
        String sub, String name, String picture, String email
) {
}
