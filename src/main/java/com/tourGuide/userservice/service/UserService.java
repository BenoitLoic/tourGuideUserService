package com.tourGuide.userservice.service;

import com.tourGuide.userservice.model.NewUserDto;
import com.tourGuide.userservice.model.User;
import java.util.List;

public interface UserService {

  /**
   * Create a new User.
   *
   * @param user the user to save
   * @return the user created, can throw DataAlreadyExistException
   */
  User createUser(NewUserDto user);

  /**
   * Get the user with the given username.
   *
   * @param userName the username
   * @return the user
   */
  User getUserByUserName(String userName);

  /**
   * Get all users saved.
   *
   * @return list of all user.
   */
  List<User> getAllUsers();
}
