package com.tourGuide.userservice.repository;

import com.tourGuide.userservice.exception.DataAlreadyExistException;
import com.tourGuide.userservice.exception.ResourceNotFoundException;
import com.tourGuide.userservice.model.User;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.stream.IntStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * Repository for UserService. contains method to read and save User. Contains method to generate
 * User for internal testing.
 */
@Repository
public class UserRepository {

  private final Logger logger = LoggerFactory.getLogger(UserRepository.class);

  protected final Map<String, User> internalUserMap = new HashMap<>();

  public UserRepository() {
    this.initializeInternalUsers();
  }

  /**
   * Get the User identified by its username.
   *
   * @param username the username
   * @return the user saved, throw ResourceNotFoundException if username doesn't exist.
   */
  public User getUserByUsername(String username) {

    if (!internalUserMap.containsKey(username)) {
      logger.warn("username : " + username + " doesn't exist.");
      throw new ResourceNotFoundException("username : " + username + " doesn't exist.");
    }

    return internalUserMap.get(username);
  }

  /**
   * Save the given user. Can throw DataAlreadyExistException if the user already exist.
   *
   * @param user the user
   */
  public void addUser(User user) {
    if (!internalUserMap.containsKey(user.getUserName())) {
      internalUserMap.put(user.getUserName(), user);
    } else {
      logger.warn("User " + user.getUserName() + " already exist.");
      throw new DataAlreadyExistException("Data already exist.");
    }
  }

  /**
   * Get all user saved.
   *
   * @return a list of user
   */
  public List<User> getAllUsers() {

    List<User> userList = new LinkedList<>();

    for (Entry<String, User> entry : internalUserMap.entrySet()) {
      userList.add(entry.getValue());
    }
    logger.debug("returning " + userList.size() + " users from repository.");
    return userList;
  }
  /**********************************************************************************
   * Methods Below will generate data for Internal Testing.
   **********************************************************************************/

  protected void initializeInternalUsers() {

    IntStream.range(0, InternalTestHelper.getInternalUserNumber())
        .forEach(
            i -> {
              String userNumber = String.format("%06d", i);
              UUID userId = UUID.fromString("0000-00-00-00-" + userNumber);
              String userName = "internalUser" + i;
              String phone = "000";
              String email = userName + "@tourGuide.com";
              User user = new User(userId, userName, phone, email);

              internalUserMap.put(userName, user);
            });
    logger.debug("Created " + InternalTestHelper.getInternalUserNumber() + " internal test users.");
  }

  private static class InternalTestHelper {

    // Set this default up to 100,000 for testing
    private static int internalUserNumber = 100;

    public static void setInternalUserNumber(int internalUserNumber) {
      InternalTestHelper.internalUserNumber = internalUserNumber;
    }

    public static int getInternalUserNumber() {
      return internalUserNumber;
    }
  }
}
