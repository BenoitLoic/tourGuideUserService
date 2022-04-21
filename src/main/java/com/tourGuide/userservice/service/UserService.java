package com.tourGuide.userservice.service;

import com.tourGuide.userservice.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    /**
     * Create a new User.
     *
     * @param user the user to save
     */
    void createUser(User user);

    /**
     * Get the user with the given username.
     * @param userName the username
     * @return the user
     */
    User getUserByUserName(String userName);

    /**
     * Get the user with the given id.
     * @param userId the id
     * @return the user
     */
    User getUserByUserId(UUID userId);

    /**
     * Get all users saved.
     * @return list of all user.
     */
    List<User> getAllUsers();


}
