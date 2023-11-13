package com.example.Friends.controllers;

import com.example.Friends.DTO.UserDTO;
import com.example.Friends.domain.User;
import com.example.Friends.domain.enums.ProfileVisibility;
import com.example.Friends.domain.enums.Role;
import com.example.Friends.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    private static final String USERNAME_PATTERN = "^[a-zA-Z0-9]{5,10}$";
    private static final Pattern usernamePattern = Pattern.compile(USERNAME_PATTERN);

    private static final String PASSWORD_PATTERN = "^[a-zA-Z0-9]{8,12}$";
    private static final Pattern passwordPattern = Pattern.compile(PASSWORD_PATTERN);

    @PostMapping("/signup")
    public ResponseEntity<User> signUp(@RequestBody UserDTO userDTO) {
        Matcher usernameMatcher = usernamePattern.matcher(userDTO.getUsername());
        Matcher passwordMatcher = passwordPattern.matcher(userDTO.getPassword());

        String illegalArgumentText = usernameMatcher.matches() ? "Username is invalid, should contain 5-10 alphanumeric characters." : passwordMatcher.matches() ? "Password is invalid, should contain 8-12 alphanumeric characters." : "Username is invalid (should be5-10 alphanumeric characters) and password is invalid (should be 8-12 alphanumeric characters).";

        if (usernameMatcher.matches() && passwordMatcher.matches()) {

            User user = User.builder()
                    .username(userDTO.getUsername())
                    .password(userDTO.getPassword())
                    .creationDate(LocalDateTime.now())
                    .profileVisibility(ProfileVisibility.PUBLIC)
                    .roles(Role.USER)
                    .build();

            userService.signUp(user);

            return ResponseEntity.ok().body(user);
        } else {
            throw new IllegalArgumentException(illegalArgumentText);
        }
    }

    @GetMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody UserDTO userDTO) {
        User user = userService.signIn(userDTO);

        if(userDTO.getPassword().equals(user.getPassword()) && userDTO.getUsername().equals(user.getUsername())) {
            return ResponseEntity.ok().body(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Data incorrect.");
        }

    };



}
