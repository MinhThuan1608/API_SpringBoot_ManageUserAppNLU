package com.fit.usernluappsbapi.reponsitory;

import com.fit.usernluappsbapi.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {



}
