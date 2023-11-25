package com.fit.usernluappsbapi.controller;

import com.fit.usernluappsbapi.model.request.ReportRequest;
import com.fit.usernluappsbapi.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequiredArgsConstructor
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/send")
    public ResponseEntity<?> sendReport(@RequestBody ReportRequest request, Authentication authentication){
        return reportService.sendReport(request, authentication.getName());
    }


}
