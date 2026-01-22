package com.mm.library.domain.reader.validations;

import com.mm.library.common.Validates;
import com.mm.library.domain.reader.Reader;
import org.springframework.stereotype.Component;

@Component
public class ValidateAlreadyExistingReader implements Validates<Reader> {
    @Override
    public void validate(Reader reader) {
        if (reader != null) {
            throw new IllegalArgumentException("Reader is already registered");
        }
    }
}
