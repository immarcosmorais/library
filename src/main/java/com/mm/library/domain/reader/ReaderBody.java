package com.mm.library.domain.reader;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ReaderBody(
        @NotBlank
        String name,
        @Email
        String email,
        @NotBlank
        String phone
) {

}
