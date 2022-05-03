package com.tourGuide.userservice.model;

import javax.validation.constraints.NotBlank;

/**
 * Dto for User creation.
 *
 * @param userName the username
 * @param phoneNumber the phone number
 * @param emailAddress the email address
 */
public record NewUserDto(
        @NotBlank String userName,
        @NotBlank String phoneNumber,
        @NotBlank String emailAddress
) {
}
