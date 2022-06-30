package com.covid.controller;

import com.covid.document.User;
import com.covid.config.AuthBody;
import com.covid.config.Register;
import com.covid.config.Roles;
import com.covid.repository.UserRepository;
import com.covid.secure.JwtTokenProvider;
import com.covid.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;


@RestController
@RequestMapping("/auth")
public class AuthApi {

    @Autowired
    AuthenticationManager auth;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserRepository users;

    @Autowired
    private CustomUserDetailsService userService;

    @SuppressWarnings("rawtypes")
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthBody data) {
        try {
            String username = data.getEmail();
            auth.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));
            String token = jwtTokenProvider.createToken(username, this.users.findByEmail(username).getRoles());
            Map<Object, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("token", token);
            return ok(model);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid email/password supplied");
        }
    }

    @SuppressWarnings("rawtypes")
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid Register register, @RequestParam Roles role) {
        User userExists = userService.findUserByEmail(register.getEmail());
        if (userExists != null) {
            throw new BadCredentialsException("User with username: " + register.getEmail() + " already exists");
        }

        User user = new User();
        user.setEmail(register.getEmail());
        user.setFullname(register.getName());
        user.setPassword(register.getPassword());
        userService.saveUser(user, role.name());
        Map<Object, Object> model = new HashMap<>();
        model.put("message", "User registered successfully");
        return ok(model);


    }
}
