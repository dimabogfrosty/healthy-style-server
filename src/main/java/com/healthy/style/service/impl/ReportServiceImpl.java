package com.healthy.style.service.impl;

import com.healthy.style.entity.Record;
import com.healthy.style.report.DateReport;
import com.healthy.style.report.Report;
import com.healthy.style.repository.RecordRepository;
import com.healthy.style.service.ReportService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.time.temporal.TemporalAdjusters.nextOrSame;
import static java.time.temporal.TemporalAdjusters.previousOrSame;

@Service
public class ReportServiceImpl implements ReportService {

    private RecordRepository recordRepository;

    @Autowired
    public void setRecordRepository(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    @Override
    public Report createUserReport(Long id) {
        return new Report(recordRepository.findRecordsByUserIdOrderByRunDate(id));
    }

    @Override
    public Report createUserReportBetweenRunDate(Long id, LocalDate from, LocalDate to) {
        return new Report(recordRepository.findRecordsByUserIdAndRunDateBetweenOrderByRunDate(id, from, to));
    }

    @Override
    public List<DateReport> createUserReportByWeek(Long id) {
        List<DateReport> dateReports = new ArrayList<>();
        List<Week> weeks = divideByWeeks(recordRepository.findRecordsByUserIdOrderByRunDate(id));
        for (Week week : weeks) {
            dateReports.add(new DateReport(recordRepository.findRecordsByUserIdAndRunDateBetweenOrderByRunDate(id,
                    week.getMonday(), week.getSunday()), week.getMonday(), week.getSunday()));
        }
        return dateReports;
    }

    private List<Week> divideByWeeks(List<Record> records) {
        List<Week> weeks = new ArrayList<>();
        LocalDate startDay = records.get(0).getRunDate();
        LocalDate endDay = records.get(records.size() - 1).getRunDate();
        while (!endDay.isBefore(startDay.with(previousOrSame(MONDAY)))) {
            weeks.add(new Week(startDay.with(previousOrSame(MONDAY)), startDay.with(nextOrSame(SUNDAY))));
            startDay = startDay.plusWeeks(1);
        }
        return weeks;
    }

    @Getter
    @RequiredArgsConstructor
    private static class Week {
        private final LocalDate monday;
        private final LocalDate sunday;
    }

}
