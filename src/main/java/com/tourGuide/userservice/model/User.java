package com.tourGuide.userservice.model;

import java.util.Objects;
import java.util.UUID;

/** Model for user entity. */
public class User {

  private UUID userId;
  private String userName;
  private String phoneNumber;
  private String emailAddress;

  public User() {}

  /**
   * Constructor with all fields.
   *
   * @param userId the user id
   * @param userName the username
   * @param phoneNumber the phone number
   * @param emailAddress the email address
   */
  public User(UUID userId, String userName, String phoneNumber, String emailAddress) {
    this.userId = userId;
    this.userName = userName;
    this.phoneNumber = phoneNumber;
    this.emailAddress = emailAddress;
  }

  public UUID getUserId() {
    return userId;
  }

  public void setUserId(UUID userId) {
    this.userId = userId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return userId.equals(user.userId)
        && Objects.equals(userName, user.userName)
        && Objects.equals(phoneNumber, user.phoneNumber)
        && Objects.equals(emailAddress, user.emailAddress);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, userName, phoneNumber, emailAddress);
  }

  @Override
  public String toString() {
    return "User{"
        + "userId="
        + userId
        + ", userName='"
        + userName
        + '\''
        + ", phoneNumber='"
        + phoneNumber
        + '\''
        + ", emailAddress='"
        + emailAddress
        + '\''
        + '}';
  }
}
