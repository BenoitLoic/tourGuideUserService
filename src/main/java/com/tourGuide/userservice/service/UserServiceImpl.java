package com.tourGuide.userservice.service;

import com.tourGuide.userservice.exception.ResourceNotFoundException;
import com.tourGuide.userservice.model.NewUserDto;
import com.tourGuide.userservice.model.User;
import com.tourGuide.userservice.repository.UserRepository;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
  private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
  @Autowired private UserRepository userRepository;

  /**
   * Create a new User.
   *
   * @param newUser the newUser to save
   * @return the user created, can throw DataAlreadyExistException
   */
  @Override
  public User createUser(NewUserDto newUser) {

    UUID userId = UUID.randomUUID();
    User user = new User(userId, newUser.userName(), newUser.phoneNumber(), newUser.emailAddress());

    userRepository.addUser(user);

    logger.info("newUser : " + newUser.userName() + " created.");

    return user;
  }

  /**
   * Get the user with the given username.
   *
   * @param username the username
   * @return the user
   */
  @Override
  public User getUserByUserName(String username) {

    return userRepository.getUserByUsername(username);
  }

  /**
   * Get all users saved.
   *
   * @return list of all user.
   */
  @Override
  public List<User> getAllUsers() {

    List<User> users = userRepository.getAllUsers();

    if (users.isEmpty()) {
      logger.warn("error, empty list : can't retrieve users data.");
      throw new ResourceNotFoundException("error, repository returned an empty List.");
    }

    return users;
  }
}
