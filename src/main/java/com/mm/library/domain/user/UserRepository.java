package com.mm.library.domain.user;

import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    UserDetails findByUsername(String username);

    Optional<User> findByEmail(String email);

    UserDetails findByUsernameAndDeletedFalse(String username);

    Optional<User> findByEmailAndDeletedFalse(@Email String email);
}
