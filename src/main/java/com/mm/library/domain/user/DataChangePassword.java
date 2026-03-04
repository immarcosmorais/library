package com.mm.library.domain.user;

import jakarta.validation.constraints.NotBlank;

public record DataChangePassword(
        @NotBlank
        String currentPassword,
        @NotBlank
        String newPassword,
        @NotBlank
        String checkNewPassword
) {
}
