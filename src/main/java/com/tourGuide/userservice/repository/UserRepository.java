package com.tourGuide.userservice.repository;


import com.tourGuide.userservice.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.*;
import java.util.stream.IntStream;

@Repository
public class UserRepository {

    private final Logger logger = LoggerFactory.getLogger(UserRepository.class);

    protected final Map<String, User> internalUserMap = new HashMap<>();

    public UserRepository() {
        this.initializeInternalUsers();
    }

    // Création de n utilisateurs. n = InternalTestHelper.internalUserNumber
    protected void initializeInternalUsers() {
        IntStream.range(0,
                InternalTestHelper.getInternalUserNumber()).forEach(i -> {
            String userName = "internalUser" + i;
            String phone = "000";
            String email = userName + "@tourGuide.com";
            User user = new User(UUID.randomUUID(), userName,
                    phone, email);

            internalUserMap.put(userName, user);
        });
        logger.debug(
                "Created " + InternalTestHelper.getInternalUserNumber() + " internal test users.");
    }


    public User getUserByUsername(String username) {


        if (!internalUserMap.containsKey(username)) {
            logger.warn("username : " + username + " doesn't exist.");
            throw new NoSuchElementException("username : " + username + " doesn't exist.");
        }

        return internalUserMap.get(username);

    }

    public void addUser(User user) {
        if (!internalUserMap.containsKey(user.getUserName())) {
            internalUserMap.put(user.getUserName(), user);
        } else {
            logger.warn("User " + user.getUserName() + " already exist.");
            throw new KeyAlreadyExistsException("Data already exist.");
        }
    }

    public List<User> getAllUsers() {

        List<User> userList = new LinkedList<>();

        for (String username : internalUserMap.keySet()) {
            userList.add(internalUserMap.get(username));
        }
        logger.debug("returning " + userList.size() + " users from repository.");
        return userList;
    }

    public static class InternalTestHelper {

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
