package com.mm.library.domain.user;

import jakarta.validation.constraints.NotBlank;

public record UserLoginData(
        @NotBlank
        String username,
        @NotBlank
        String password
) {
}
