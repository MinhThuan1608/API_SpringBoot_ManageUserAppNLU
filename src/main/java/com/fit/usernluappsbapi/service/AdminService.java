package com.fit.usernluappsbapi.service;

import com.fit.usernluappsbapi.model.User;
import com.fit.usernluappsbapi.model.request.AuthenticationRequest;
import com.fit.usernluappsbapi.model.response.Response;
import com.fit.usernluappsbapi.model.response.UserResponse;
import com.fit.usernluappsbapi.reponsitory.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;

    public List<UserResponse> getAllUser() {
        List<UserResponse> res = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            res.add(new UserResponse(user));
        }
        return res;
    }

    public ResponseEntity<?> addManager(AuthenticationRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent())
            return ResponseEntity.ok(new Response("Add manager failed! Username already exist.", null));
        var manager = User.builder().username(request.getUsername())
                .password(new BCryptPasswordEncoder().encode(request.getPassword()))
                .isVip(false)
                .expiredVipDate(null)
                .isNonLocked(true)
                .role(User.Role.MANAGER).build();
        return ResponseEntity.ok(new Response("Add manager success!", new UserResponse(userRepository.save(manager))));
    }


    public ResponseEntity<?> lockUser(String id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) return ResponseEntity.ok(new Response("Username does not exist!", null));
        User user = userOptional.get();
        if (user.getRole().equals(User.Role.ADMIN))
            return ResponseEntity.ok(new Response("You do not have permission to do this!", null));
        user.setNonLocked(false);
        return ResponseEntity.ok(new Response("Lock user completed!", new UserResponse(userRepository.save(user))));
    }

    public ResponseEntity<?> unlockUser(String id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) return ResponseEntity.ok(new Response("Username does not exist!", null));
        User user = userOptional.get();
        user.setNonLocked(false);
        return ResponseEntity.ok(new Response("Unlock user completed!", new UserResponse(userRepository.save(user))));
    }

    public ResponseEntity<?> deleteUser(String id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) return ResponseEntity.ok(new Response("Username does not exist!", null));
        User user = userOptional.get();
        if (user.getRole().equals(User.Role.ADMIN))
            return ResponseEntity.ok(new Response("You do not have permission to do this!", null));
        userRepository.delete(user);
        return ResponseEntity.ok(new Response("Delete user completed!", new UserResponse(user)));
    }


}
