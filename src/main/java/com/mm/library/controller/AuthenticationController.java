package com.mm.library.controller;

import com.mm.library.configuration.security.TokenJWT;
import com.mm.library.configuration.security.TokenService;
import com.mm.library.domain.user.DataChangePassword;
import com.mm.library.domain.user.User;
import com.mm.library.domain.user.UserLoginData;
import com.mm.library.domain.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("autentication")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid UserLoginData data) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        Authentication authentication = manager.authenticate(authToken);
        String token = tokenService.createToken((User) authentication.getPrincipal());
        return ResponseEntity.ok(new TokenJWT(token));
    }

    @PostMapping("/change-password")
    public ResponseEntity changePassword(@RequestBody @Valid DataChangePassword data, @AuthenticationPrincipal User user) {
        this.userService.changePassword(data, user);
        return ResponseEntity.noContent().build();
    }


}
