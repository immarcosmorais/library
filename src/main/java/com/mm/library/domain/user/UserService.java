package com.mm.library.domain.user;

import com.mm.library.domain.reader.ReaderBody;
import com.mm.library.domain.user.validations.ValidateAlreadyExistingUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsernameAndDeletedFalse(username);
    }

    public User create(ReaderBody readerBody) {
        User user = new User(new ReaderBody(
                readerBody.name(),
                readerBody.email(),
                readerBody.phone(),
                passwordEncoder.encode(readerBody.password())
        ));
       return this.save(user);
    }

    public User update(ReaderBody readerBody) {
        User user = this.userRepository.findByEmailAndDeletedFalse(readerBody.email())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + readerBody.email()));
        user.setName(readerBody.name());
        user.setEmail(readerBody.phone());
        user.setUsername(readerBody.email());
        if (readerBody.password() != null && !readerBody.password().isEmpty()) {
            user.setPassword(passwordEncoder.encode(readerBody.password()));
        }
        return this.userRepository.save(user);
    }

    public User save(User user) {
        User userToChack = this.findByEmail(user.getEmail()).orElse(null);
        new ValidateAlreadyExistingUser().validate(userToChack);
        return this.userRepository.save(user);
    }

    public void update(User userToBeDeleted) {
        this.userRepository.save(userToBeDeleted);
    }

    public Optional<User> findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }
}
