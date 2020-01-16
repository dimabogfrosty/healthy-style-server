package com.healthy.style.service;

import com.healthy.style.entity.User;
import com.healthy.style.report.Report;

public interface ReportService {

    Report createUserReport(User user);

}
