package com.mm.library.domain.author;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record AuthorBody(
        @NotBlank
        String name,
        @NotNull
        Date birthDate
) {

}
