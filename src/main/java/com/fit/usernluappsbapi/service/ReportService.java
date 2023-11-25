package com.fit.usernluappsbapi.service;

import com.fit.usernluappsbapi.model.Report;
import com.fit.usernluappsbapi.model.User;
import com.fit.usernluappsbapi.model.request.ReportRequest;
import com.fit.usernluappsbapi.model.response.Response;
import com.fit.usernluappsbapi.reponsitory.ReportRepository;
import com.fit.usernluappsbapi.reponsitory.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;

    public ResponseEntity<?> sendReport(ReportRequest request, String username) {
        long id=  reportRepository.count();
        User user = userRepository.findByUsername(username).orElseThrow();
        Report report = Report.builder()
                .id(id)
                .message(request.getMessage())
                .isRead(false)
                .isStar(false)
                .createDate(new Date())
                .user(user).build();
        reportRepository.save(report);
        return ResponseEntity.ok(new Response("Your report is sent", null));
    }
}
