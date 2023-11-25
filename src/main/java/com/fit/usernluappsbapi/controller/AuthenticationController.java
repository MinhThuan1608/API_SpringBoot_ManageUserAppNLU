package com.fit.usernluappsbapi.controller;

import com.fit.usernluappsbapi.model.User;
import com.fit.usernluappsbapi.model.request.AuthenticationRequest;
import com.fit.usernluappsbapi.model.response.UserResponse;
import com.fit.usernluappsbapi.service.JwtService;
import com.fit.usernluappsbapi.model.response.Response;
import com.fit.usernluappsbapi.model.response.LoginResponse;
import com.fit.usernluappsbapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authenticate")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtTokenUtil;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (LockedException le) {
            return ResponseEntity.ok(new Response("Your account has been locked! Please call 0359681217 to get support!", null));
        } catch (BadCredentialsException bce) {
            return ResponseEntity.ok(new Response("Wrong username or password", null));
        }
        final User user = userService.loadUserByUsername(request.getUsername());
        if (user.getExpiredVipDate() != null)
            if (user.getExpiredVipDate().before(new Date())) userService.removeVip(user.getUsername());
        final String token = jwtTokenUtil.generateToken(user);
        return ResponseEntity.ok(new Response("Login success!", new LoginResponse(token)));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthenticationRequest request) {
        return userService.register(request);
    }


}
