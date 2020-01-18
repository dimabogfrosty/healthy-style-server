package com.healthy.style.service;

import com.healthy.style.report.DateReport;
import com.healthy.style.report.Report;

import java.time.LocalDate;
import java.util.List;

public interface ReportService {

    Report createUserReport(Long id);

    Report createUserReportBetweenRunDate(Long id, LocalDate from, LocalDate to);

    List<DateReport> createUserReportByWeek(Long id);

    List<DateReport> createUserReportByMonth(Long id);

}
