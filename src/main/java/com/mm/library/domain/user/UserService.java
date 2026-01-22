package com.mm.library.domain.user;

import com.mm.library.domain.reader.ReaderBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username);
    }

    public Long createUserForReader(ReaderBody readerBody) {
        User user = new User(new ReaderBody(
            readerBody.name(),
            readerBody.email(),
            passwordEncoder.encode(readerBody.password()),
            readerBody.phone()
        ));
        User savedUser = this.userRepository.save(user);
        return savedUser.getId();
    }
}
