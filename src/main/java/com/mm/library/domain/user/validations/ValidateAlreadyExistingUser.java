package com.mm.library.domain.user.validations;

import com.mm.library.common.Validates;
import com.mm.library.domain.user.User;
import org.springframework.stereotype.Component;

@Component
public class ValidateAlreadyExistingUser implements Validates<User> {
    @Override
    public void validate(User user) {
        if (user != null) {
            throw new IllegalArgumentException("User is already registered");
        }
    }
}
