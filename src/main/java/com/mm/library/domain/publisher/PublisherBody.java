package com.mm.library.domain.publisher;

import jakarta.validation.constraints.NotBlank;

public record PublisherBody(
        @NotBlank
        String name,
        @NotBlank
        String country
) {
}
