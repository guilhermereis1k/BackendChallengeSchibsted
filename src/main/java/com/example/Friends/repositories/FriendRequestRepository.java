package com.example.Friends.repositories;

import com.example.Friends.domain.FriendRequest;
import com.example.Friends.domain.User;
import com.example.Friends.domain.enums.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Collection;

@EnableJpaRepositories
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    FriendRequest findBySenderAndReceiver(User sender, User receiver);

    Collection<? extends FriendRequest> findByReceiverAndRequestStatus(User user, RequestStatus requestStatus);

    Collection<? extends FriendRequest> findBySenderAndRequestStatus(User user, RequestStatus requestStatus);
}
