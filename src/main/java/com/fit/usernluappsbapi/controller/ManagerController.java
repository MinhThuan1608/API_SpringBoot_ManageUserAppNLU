package com.fit.usernluappsbapi.controller;

import com.fit.usernluappsbapi.model.User;
import com.fit.usernluappsbapi.model.request.AddVipRequest;
import com.fit.usernluappsbapi.model.request.ChangePasswordRequest;
import com.fit.usernluappsbapi.model.response.Response;
import com.fit.usernluappsbapi.model.response.UserResponse;
import com.fit.usernluappsbapi.service.ManagerService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequiredArgsConstructor
@RequestMapping("/manage")
public class ManagerController {

    private final ManagerService managerService;

    @GetMapping("/users/getAll")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(new Response("Get all users success!" , managerService.getAllUserNotAdmin()));
    }

    @PostMapping("/users/lock/{id}")
    public ResponseEntity<?> lockUser(@PathVariable String id, Authentication authentication){
        if (authentication.getName().equals(id)) return ResponseEntity.ok(new Response("You can not lock yourself!", null));
        return managerService.lockUser(id);
    }

    @PostMapping("/users/unlock/{id}")
    public ResponseEntity<?> unlockUser(@PathVariable String id){
        return managerService.unlockUser(id);
    }

    @PostMapping("/users/addVip")
    public ResponseEntity<?> setVip(@RequestBody AddVipRequest request){
        return managerService.setVip(request.getUsername(), request.getDays());
    }

    @PostMapping("/users/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest, Authentication authentication){
        return managerService.changePassword(changePasswordRequest, authentication.getName());
    }

    @PostMapping("/report/read/{id}")
    public ResponseEntity<?> readReport(@PathVariable Long id, Authentication authentication){
        return managerService.readReport(id, authentication.getName());
    }

    @PostMapping("/report/grantStar/{id}")
    public ResponseEntity<?> grantStarReport(@PathVariable Long id){
        return managerService.grantStar(id);
    }

    @PostMapping("/report/rmStar/{id}")
    public ResponseEntity<?> rmStarReport(@PathVariable Long id){
        return managerService.rmStar(id);
    }

    @PostMapping("/report/delete/{id}")
    public ResponseEntity<?> deleteReport(@PathVariable Long id){
        return managerService.rmStar(id);
    }

}
