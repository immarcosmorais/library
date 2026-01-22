package com.mm.library.domain.user;

import com.mm.library.common.Validates;
import com.mm.library.domain.reader.Reader;
import com.mm.library.domain.reader.ReaderBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    List<Validates<User>> validateUsers;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username);
    }

    public User createUserForReader(ReaderBody readerBody) {
        User user = new User(new ReaderBody(
                readerBody.name(),
                readerBody.email(),
                readerBody.phone(),
                passwordEncoder.encode(readerBody.password())
        ));
       return this.save(user);
    }

    public User save(User user) {
        User userToChack = this.findByEmail(user.getEmail()).orElse(null);
        validateUsers.forEach(v -> v.validate(userToChack));
        return this.userRepository.save(user);
    }

    public void update(User userToBeDeleted) {
        this.userRepository.save(userToBeDeleted);
    }

    public Optional<User> findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }
}
