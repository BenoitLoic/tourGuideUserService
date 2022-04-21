package com.tourGuide.userservice.service;

import com.tourGuide.userservice.model.User;
import com.tourGuide.userservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    /**
     * Create a new User.
     *
     * @param user the user to save
     */
    @Override
    public void createUser(User user) {

        if (user.getUserName().isBlank()) {
            logger.warn("username cannot be blank/empty");
            throw new IllegalArgumentException("username cannot be blank/empty");
        }
        userRepository.addUser(user);
        logger.info("user : " + user.getUserName() + " created.");

    }

    /**
     * Get the user with the given username.
     *
     * @param username the username
     * @return the user
     */
    @Override
    public User getUserByUserName(String username) {

        if (username.isBlank()) {
            logger.warn("username can't be null or blank. username : " + username);
            throw new IllegalArgumentException("username can't be null or blank");
        }

        return userRepository.getUserByUsername(username);
    }

    /**
     * Get the user with the given id.
     *
     * @param userId the id
     * @return the user
     */
    @Override
    public User getUserByUserId(UUID userId) {
        return null;
    }

    /**
     * Get all users saved.
     *
     * @return list of all user.
     */
    @Override
    public List<User> getAllUsers() {

        return userRepository.getAllUsers();
    }


}
