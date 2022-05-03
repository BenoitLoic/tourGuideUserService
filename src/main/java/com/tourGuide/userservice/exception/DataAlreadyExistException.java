package com.tourGuide.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception with Http error 409.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class DataAlreadyExistException extends RuntimeException {
  public DataAlreadyExistException(String message) {
    super(message);
  }
}
