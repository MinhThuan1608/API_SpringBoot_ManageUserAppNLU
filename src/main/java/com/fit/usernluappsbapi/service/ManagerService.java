package com.fit.usernluappsbapi.service;

import com.fit.usernluappsbapi.model.Report;
import com.fit.usernluappsbapi.model.User;
import com.fit.usernluappsbapi.model.request.ChangePasswordRequest;
import com.fit.usernluappsbapi.model.response.Response;
import com.fit.usernluappsbapi.model.response.UserResponse;
import com.fit.usernluappsbapi.reponsitory.ReportRepository;
import com.fit.usernluappsbapi.reponsitory.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ManagerService {

    private final UserRepository userRepository;
    private final ReportRepository reportRepository;
    private final PasswordEncoder passwordEncoder;

    public List<UserResponse> getAllUserNotAdmin() {
        List<UserResponse> res = new ArrayList<>();
        for (User user : userRepository.findAllByRoleNot(User.Role.ADMIN)) {
            res.add(new UserResponse(user));
        }
        return res;
    }

    public ResponseEntity<?> lockUser(String id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) return ResponseEntity.ok(new Response("Username does not exist!", null));
        User user = userOptional.get();
        if (user.getRole().equals(User.Role.ADMIN) || user.getRole().equals(User.Role.MANAGER))
            return ResponseEntity.ok(new Response("You do not have permission to do this!", null));
        user.setNonLocked(false);
        return ResponseEntity.ok(new Response("Lock user completed!", new UserResponse(userRepository.save(user))));
    }

    public ResponseEntity<?> unlockUser(String id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) return ResponseEntity.ok(new Response("Username does not exist!", null));
        User user = userOptional.get();
        if (user.getRole().equals(User.Role.ADMIN) || user.getRole().equals(User.Role.MANAGER))
            return ResponseEntity.ok(new Response("You do not have permission to do this!", null));
        user.setNonLocked(false);
        return ResponseEntity.ok(new Response("Unlock user completed!", new UserResponse(userRepository.save(user))));
    }

    public ResponseEntity<?> setVip(String username, int days) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (!userOptional.isPresent()) return ResponseEntity.ok(new Response("Username does not exist!", null));
        User user = userOptional.get();
        user.setVip(true);
        Date date = user.getExpiredVipDate();
        if (date == null) date = new Date();
        user.setExpiredVipDate(new Date(date.getTime() + (long) days * 24 * 60 * 60 * 1000));
        return ResponseEntity.ok(new Response("User '" + username + "' has been added " + days + " for VIP", new UserResponse(userRepository.save(user))));
    }


    public ResponseEntity<?> changePassword(ChangePasswordRequest changePasswordRequest, String username) {
        String oldPassHashed = BCrypt.hashpw(changePasswordRequest.getOld_pass(), BCrypt.gensalt());
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (!userOptional.isPresent()) return ResponseEntity.ok(new Response("Username does not exist!", null));
        User user = userOptional.get();
        if (!passwordEncoder.matches(changePasswordRequest.getOld_pass(), user.getPassword())) return ResponseEntity.ok(new Response("Old password is incorrect!", null));
//        if (!BCrypt.checkpw(changePasswordRequest.getOld_pass(), oldPassHashed)) return ResponseEntity.ok(new Response("Old password is incorrect!", null));
        if (!changePasswordRequest.getNew_pass().equals(changePasswordRequest.getRe_new_pass())) return ResponseEntity.ok(new Response("New password do not same renew password!", null));
        user.setPassword(BCrypt.hashpw(changePasswordRequest.getNew_pass(), BCrypt.gensalt()));
        return ResponseEntity.ok(new Response("Change password is success!", new UserResponse(userRepository.save(user))));

    }

    public ResponseEntity<?> readReport(Long id, String readerName) {
        var reportOptional = reportRepository.findById(id);
        if (!reportOptional.isPresent()) return ResponseEntity.ok(new Response("Report id does not exist!", null));
        Report report = reportOptional.get();
        report.setRead(true);
        report.setReaderName(readerName);
        reportRepository.save(report);
        return ResponseEntity.ok(new Response("Report '"+id+"' is read!", null));
    }

    public ResponseEntity<?> grantStar(Long id) {
        var reportOptional = reportRepository.findById(id);
        if (!reportOptional.isPresent()) return ResponseEntity.ok(new Response("Report id does not exist!", null));
        Report report = reportOptional.get();
        report.setStar(true);
        reportRepository.save(report);
        return ResponseEntity.ok(new Response("Report '"+id+"' is granted star!", null));
    }

    public ResponseEntity<?> rmStar(Long id) {
        var reportOptional = reportRepository.findById(id);
        if (!reportOptional.isPresent()) return ResponseEntity.ok(new Response("Report id does not exist!", null));
        Report report = reportOptional.get();
        report.setStar(false);
        reportRepository.save(report);
        return ResponseEntity.ok(new Response("Report '"+id+"' is removed star!", null));
    }

    public ResponseEntity<?> deleteReport(Long id) {
        var reportOptional = reportRepository.findById(id);
        if (!reportOptional.isPresent()) return ResponseEntity.ok(new Response("Report id does not exist!", null));
        Report report = reportOptional.get();
        reportRepository.delete(report);
        return ResponseEntity.ok(new Response("Report '"+id+"' is removed star!", null));
    }
}
