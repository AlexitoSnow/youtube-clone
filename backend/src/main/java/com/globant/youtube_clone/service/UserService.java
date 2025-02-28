package com.globant.youtube_clone.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.globant.youtube_clone.dto.UserInfoDTO;
import com.globant.youtube_clone.mappers.UserMapper;
import com.globant.youtube_clone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service @RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    @Value("${auth0.userinfoEndpoint}")
    private String userInfoEndpoint;
    private final UserMapper mapper;

    public void registerUser(String token) {
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(userInfoEndpoint))
                .setHeader("Authorization", "Bearer " + token)
                .build();
        var httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
        try {
            var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String body = response.body();
            ObjectMapper jsonMapper = new ObjectMapper();
            jsonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            var userDTO = jsonMapper.readValue(body, UserInfoDTO.class);
            repository.save(mapper.fromDTO(userDTO));

        } catch (Exception e) {
            throw new RuntimeException("Exception occurred while registering user", e.getCause());
        }
    }
}
