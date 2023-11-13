package com.example.Friends.controllers;

import com.example.Friends.DTO.UserDTO;
import com.example.Friends.domain.FriendRequest;
import com.example.Friends.domain.User;
import com.example.Friends.domain.enums.ProfileVisibility;
import com.example.Friends.domain.enums.RequestStatus;
import com.example.Friends.services.FriendRequestService;
import com.example.Friends.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
public class FriendRequestController {

    @Autowired
    FriendRequestService friendRequestService;

    @Autowired
    UserService userService;

    @PostMapping("/{receiver}/send-request")
    public ResponseEntity<?> sendRequest(@PathVariable String receiver, @RequestBody UserDTO userDTO) {
        User receiverUser = userService.findByUsername(receiver);
        User senderUser = userService.findByUsername(userDTO.getUsername());

        if (receiverUser.getProfileVisibility() == ProfileVisibility.HIDDEN) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("This user can't receive friendship requests.");
        }

        FriendRequest friendRequest = FriendRequest
                .builder()
                .sender(senderUser)
                .receiver(receiverUser)
                .requestStatus(RequestStatus.PENDING)
                .build();

        FriendRequest savedRequest = friendRequestService.sendRequest(friendRequest);

        friendRequest.getSender().getFriendRequestSent().add(savedRequest);
        friendRequest.getReceiver().getFriendRequestReceived().add(savedRequest);

        return ResponseEntity.ok().body(savedRequest);

    }

    @GetMapping("/{username}/friend-requests")
    public ResponseEntity<Set<FriendRequest>> getFriendRequests(@PathVariable String username) {
        User user = userService.findByUsername(username);

        if (user != null) {
            Set<FriendRequest> friendRequests = friendRequestService.getFriendRequestsForUser(user);
            return ResponseEntity.ok().body(friendRequests);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
