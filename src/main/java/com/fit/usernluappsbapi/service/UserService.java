package com.fit.usernluappsbapi.service;

import com.fit.usernluappsbapi.model.request.AuthenticationRequest;
import com.fit.usernluappsbapi.model.response.Response;
import com.fit.usernluappsbapi.model.response.UserResponse;
import com.fit.usernluappsbapi.reponsitory.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.fit.usernluappsbapi.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User loadUserByUsername(String username) {
        var u = userRepository.findByUsername(username).orElseThrow();
        return u;
//            return new org.springframework.security.core.userdetails.User(u.getUsername(), u.getPassword(), true, true, true, true, Arrays.asList(new SimpleGrantedAuthority("ROLE_" + u.getRole())));
//        return new org.springframework.security.core.userdetails.User(u.getUsername(), u.getPassword(), true, true, true, false, Arrays.asList(new SimpleGrantedAuthority("ROLE_" + u.getRole())));

    }

    public ResponseEntity<?> register(AuthenticationRequest request) {
        if (userRepository.existsById(request.getUsername())) return ResponseEntity.ok(new Response("Register failed! Username already exist.", null));
        var user = User.builder().username(request.getUsername())
                .password(new BCryptPasswordEncoder().encode(request.getPassword()))
                .isVip(false)
                .expiredVipDate(null)
                .isNonLocked(true)
                .role(User.Role.USER).build();
        return ResponseEntity.ok(new Response("Register success!", new UserResponse(userRepository.save(user))));
    }

    public void removeVip(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (!userOptional.isPresent()) return;
        User user = userOptional.get();
        user.setVip(false);
        user.setExpiredVipDate(null);
        userRepository.save(user);
    }
}
