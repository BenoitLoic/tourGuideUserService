package com.tourGuide.userservice.model;

import javax.validation.constraints.NotBlank;

public record NewUserDto(
        @NotBlank String userName,
        @NotBlank String phoneNumber,
        @NotBlank String emailAddress
) {
}
