package com.mm.library.domain.user;

import jakarta.validation.constraints.NotBlank;

public record RecoveryAccountData(
        @NotBlank String newPassword, @NotBlank String checkNewPassword
) {
}
