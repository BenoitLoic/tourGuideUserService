package com.tourGuide.userservice.controller;

import com.tourGuide.userservice.exception.IllegalArgumentException;
import com.tourGuide.userservice.model.NewUserDto;
import com.tourGuide.userservice.model.User;
import com.tourGuide.userservice.service.UserService;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/** Rest controller for UserService Api. */
@RestController
public class UserController {

  private final Logger logger = LoggerFactory.getLogger(UserController.class);
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  /**
   * Save the user.
   *
   * @param user the user
   * @return the user object saved with its UUID , can throw MethodArgumentNotValidException
   */
  @PostMapping("/addUser")
  @ResponseStatus(HttpStatus.CREATED)
  public User addUser(@Valid @RequestBody NewUserDto user) {
    return userService.createUser(user);
  }

  /**
   * Get all users.
   *
   * @return list of saved User , can throw ResourceNotFoundException if there is an error
   */
  @GetMapping("/getAllUsers")
  public List<User> getAllUsers() {

    return userService.getAllUsers();
  }

  /**
   * Get user identified by this username.
   *
   * @param username the username (not blank, not null)
   * @return the user, can throw IllegalArgumentException - ResourceNotFoundException
   */
  @GetMapping("/getUserByUsername")
  public User getUserByUsername(@RequestParam String username) {

    if (username.isBlank()) {
      logger.warn("Error, username can't be blank or null. username : " + username);
      throw new IllegalArgumentException("error, username can't be null or blank.");
    }

    return userService.getUserByUserName(username);
  }
}
