package com.example.Friends.services;

import com.example.Friends.DTO.UserDTO;
import com.example.Friends.domain.User;
import com.example.Friends.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Base64;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User signUp(User user) {
        return userRepository.save(user);
    }

    public User signIn(UserDTO userDTO) {
        return userRepository.findByUsername(userDTO.getUsername());
    }

    public User findByUsername(String username) {
        try {
            return userRepository.findByUsername(username);
        }
        catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
