package com.globant.youtube_clone.controller;

import com.globant.youtube_clone.dto.UserDisplay;
import com.globant.youtube_clone.dto.UserInfoDTO;
import com.globant.youtube_clone.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("api/user")
@AllArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public UserInfoDTO register(Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        return service.registerUser(jwt.getTokenValue());
    }

    @PutMapping("/subscribe/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Boolean subscribeUser(@PathVariable String userId) {
        service.subscribeUser(userId);
        return true;
    }

    @PutMapping("/unsubscribe/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Boolean unsubscribeUser(@PathVariable String userId) {
        service.unsubscribeUser(userId);
        return true;
    }

    @GetMapping("/logged")
    @ResponseStatus(HttpStatus.OK)
    public UserInfoDTO getUserLogged() {
        return service.getLoggedUser();
    }

    @GetMapping("/{userId}/history")
    @ResponseStatus(HttpStatus.OK)
    public Set<String> videoHistory(@PathVariable String userId) {
        return service.getVideoHistory(userId);
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserDisplay getUser(@PathVariable String userId) {
        return service.getUserInfo(userId);
    }
}