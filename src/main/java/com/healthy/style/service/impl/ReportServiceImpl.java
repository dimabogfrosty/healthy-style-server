package com.healthy.style.service.impl;

import com.healthy.style.entity.User;
import com.healthy.style.report.Report;
import com.healthy.style.repository.RecordRepository;
import com.healthy.style.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {

    private RecordRepository recordRepository;

    @Autowired
    public void setRecordRepository(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    @Override
    public Report createUserReport(User user) {
        return new Report(recordRepository.findRecordsByUser(user));
    }

}
