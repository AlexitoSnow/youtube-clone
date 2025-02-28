package com.globant.youtube_clone.controller;

import com.globant.youtube_clone.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController @RequestMapping("api/user") @AllArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping("/register") @ResponseStatus(HttpStatus.OK)
    public String register(Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        service.registerUser(jwt.getTokenValue());
        return "";
    }

    @PutMapping("subscribe/{userId}") @ResponseStatus(HttpStatus.OK)
    public void subscribeUser(@PathVariable String userId) {
        service.subscribeUser(userId);
    }

    @PutMapping("unsubscribe/{userId}") @ResponseStatus(HttpStatus.OK)
    public void unsubscribeUser(@PathVariable String userId) {
        service.unsubscribeUser(userId);
    }

    @GetMapping("{userId}/history") @ResponseStatus(HttpStatus.OK)
    public Set<String> videoHistory(@PathVariable String userId) {
        return service.getVideoHistory(userId);
    }
}
