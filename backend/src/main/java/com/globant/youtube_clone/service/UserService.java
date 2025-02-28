package com.globant.youtube_clone.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.globant.youtube_clone.dto.UserInfoDTO;
import com.globant.youtube_clone.mappers.UserMapper;
import com.globant.youtube_clone.model.User;
import com.globant.youtube_clone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Set;

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

    public User getCurrent() {
        String sub = ((Jwt) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getClaim("sub");
        return repository.findBySub(sub).orElseThrow(() -> new IllegalArgumentException("Cannot find user with sub - " + sub ));
    }

    public boolean ifLikedVideo(String videoId) {
        return getCurrent().getLikedVideos().stream().anyMatch(videoId::equals);
    }

    public boolean ifUnlikedVideo(String videoId) {
        return getCurrent().getUnlikedVideos().stream().anyMatch(videoId::equals);
    }

    public void addToLikedVideos(String videoId) {
        User current = getCurrent();
        current.addLikedVideo(videoId);
        repository.save(current);
    }

    public void removeFromLikedVideos(String videoId) {
        User current = getCurrent();
        current.removeFromLikedVideo(videoId);
        repository.save(current);
    }

    public void addToUnlikedVideos(String videoId) {
        User current = getCurrent();
        current.addUnLikedVideo(videoId);
        repository.save(current);
    }

    public void removeFromUnlikedVideos(String videoId) {
        User current = getCurrent();
        current.removeFromUnlikedVideo(videoId);
        repository.save(current);
    }

    public void addToWatchHistory(String videoId) {
        User current = getCurrent();
        current.addToHistory(videoId);
        repository.save(current);
    }

    public void subscribeUser(String userId) {
        var targetUser = repository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Not user found with id - " + userId));
        var current = getCurrent();
        current.subscribeTo(userId);
        targetUser.addSubscriber(current.getId());
        repository.save(current);
        repository.save(targetUser);
    }

    public void unsubscribeUser(String userId) {
        var targetUser = repository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Not user found with id - " + userId));
        var current = getCurrent();
        current.unsubscribeFrom(userId);
        targetUser.removeSubscriber(current.getId());
        repository.save(current);
        repository.save(targetUser);
    }

    public Set<String> getVideoHistory(String userId) {
        return getCurrent().getVideoHistory();
    }
}
