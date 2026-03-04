package com.mm.library.domain.user;

import com.mm.library.domain.reader.ReaderBody;
import com.mm.library.domain.user.email.EmailService;
import com.mm.library.domain.user.validations.ValidateAlreadyExistingUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

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

    public void changePassword(DataChangePassword data, User user){
        if(this.passwordEncoder.matches(data.currentPassword(), user.getPassword())){
            throw new IllegalArgumentException("Passwords don't match");
        }
        if (data.newPassword().equals(data.checkNewPassword())) {
            throw new IllegalArgumentException("Passwords don't match");
        }
        user.setPassword(passwordEncoder.encode(data.newPassword()));
        this.userRepository.save(user);
    }

    public void sendToken(String email){
        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        String token = UUID.randomUUID().toString();
        user.setToken(token);
        user.setTokenExpiration(LocalDateTime.now().plusMinutes(30));
        this.userRepository.save(user);
        this.emailService.sentEmailWithPassword(user);
    }

    public void recoveryAccount(String token, RecoveryAccountData data) {
       User user = this.userRepository.findByTokenIgnoreCase(token).orElseThrow(() -> new UsernameNotFoundException("User not found with code: " + token));
       if (user.getTokenExpiration().isBefore(LocalDateTime.now())) {
           throw new IllegalArgumentException("Token is expired");
       }
       if (data.newPassword().equals(data.checkNewPassword())) {
           throw new IllegalArgumentException("Passwords don't match");
       }
       user.setToken(null);
       user.setTokenExpiration(null);
       user.setPassword(passwordEncoder.encode(data.newPassword()));
       this.userRepository.save(user);
    }
}
