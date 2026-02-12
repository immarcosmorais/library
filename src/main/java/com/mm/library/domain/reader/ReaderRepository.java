package com.mm.library.domain.reader;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, Long> {

    Page<Reader> findAllByDeletedFalse(Pageable pageable);

    Optional<Reader> findByIdAndDeletedFalse(Long Id);

    Optional<Reader> findByEmail(@Email String email);

    Optional<Reader> findByEmailAndNameAndPhone(@Email String email, @NotBlank String name, @NotBlank String phone);

    Optional<Reader> findByEmailOrNameOrPhone(@Email String email, @NotBlank String name, @NotBlank String phone);

    Optional<Reader> findByEmailOrPhone(@Email String email, @NotBlank String phone);

    Optional<Reader> findByEmailAndDeletedFalse(String username);
}
