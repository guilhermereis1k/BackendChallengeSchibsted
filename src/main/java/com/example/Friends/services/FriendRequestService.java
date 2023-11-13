package com.example.Friends.services;

import com.example.Friends.domain.FriendRequest;
import com.example.Friends.domain.User;
import com.example.Friends.domain.enums.RequestStatus;
import com.example.Friends.repositories.FriendRequestRepository;
import com.example.Friends.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class FriendRequestService {

    @Autowired
    FriendRequestRepository friendRequestRepository;

    @Autowired
    UserRepository userRepository;

    public FriendRequest sendRequest(FriendRequest friendRequest) {
        return friendRequestRepository.save(friendRequest);
    }

    public Set<FriendRequest> getFriendRequestsForUser(User user) {
        Set<FriendRequest> friendRequests = new HashSet<>();

        friendRequests.addAll(friendRequestRepository.findBySenderAndRequestStatus(user, RequestStatus.PENDING));

        friendRequests.addAll(friendRequestRepository.findByReceiverAndRequestStatus(user, RequestStatus.PENDING));

        return friendRequests;
    }
}
