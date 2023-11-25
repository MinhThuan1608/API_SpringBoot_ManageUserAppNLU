package com.fit.usernluappsbapi.controller;

import com.fit.usernluappsbapi.model.User;
import com.fit.usernluappsbapi.model.request.AuthenticationRequest;
import com.fit.usernluappsbapi.model.response.Response;
import com.fit.usernluappsbapi.model.response.UserResponse;
import com.fit.usernluappsbapi.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(new Response("Get all users success!", adminService.getAllUser()));
    }

    @PostMapping("/addManager")
    public ResponseEntity<?> addManagerAccount(@RequestBody AuthenticationRequest request) {
        return adminService.addManager(request);
    }

    @PostMapping("/lock/{id}")
    public ResponseEntity<?> lockUser(@PathVariable String id, Authentication authentication){
        if (authentication.getName().equals(id)) return ResponseEntity.ok(new Response("You can not lock yourself!", null));
        return adminService.lockUser(id);
    }

    @PostMapping("/unlock/{id}")
    public ResponseEntity<?> unlockUser(@PathVariable String id){
        return adminService.unlockUser(id);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id, Authentication authentication){
        if (authentication.getName().equals(id)) return ResponseEntity.ok(new Response("You can not delete yourself!", null));
        return adminService.deleteUser(id);
    }
}
