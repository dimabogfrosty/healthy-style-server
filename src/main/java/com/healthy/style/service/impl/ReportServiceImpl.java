package com.healthy.style.service.impl;

import com.healthy.style.entity.Record;
import com.healthy.style.report.DateReport;
import com.healthy.style.report.Report;
import com.healthy.style.repository.RecordRepository;
import com.healthy.style.service.ReportService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.time.temporal.TemporalAdjusters.*;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final RecordRepository recordRepository;

    @Override
    public Report createUserReport(final Long id) {
        List<Record> records = recordRepository.findRecordsByUserIdOrderByRunDate(id);
        if(records.isEmpty()) return null;
        return new Report(records);
    }

    @Override
    public Report createUserReportBetweenRunDate(final Long id, final LocalDate from, final LocalDate to) {
        List<Record> records = recordRepository.findRecordsByUserIdAndRunDateBetweenOrderByRunDate(id, from, to);
        if(records.isEmpty()) return null;
        return new Report(records);
    }

    @Override
    public List<DateReport> createUserReportByWeek(final Long id) {
        List<Record> records = recordRepository.findRecordsByUserIdOrderByRunDate(id);
        if(records.isEmpty()) return new ArrayList<>();
        return getDateReports(id, divideByWeeks(records));
    }

    @Override
    public List<DateReport> createUserReportByMonth(final Long id) {
        List<Record> records = recordRepository.findRecordsByUserIdOrderByRunDate(id);
        if(records.isEmpty()) return new ArrayList<>();
        return getDateReports(id, divideByMonths(records));
    }

    private List<DateReport> getDateReports(final Long id, final List<DayInterval> dayIntervals) {
        List<DateReport> dateReports = new ArrayList<>();
        List<Record> records;

        for (DayInterval dayInterval : dayIntervals) {
            records = recordRepository.findRecordsByUserIdAndRunDateBetweenOrderByRunDate(id,
                    dayInterval.getStart(),
                    dayInterval.getEnd());

            if (!records.isEmpty()) {
                dateReports.add(new DateReport(records, dayInterval.getStart(), dayInterval.getEnd()));
            }
        }

        return dateReports;
    }

    private List<DayInterval> divideByWeeks(final List<Record> records) {
        List<DayInterval> dayIntervals = new ArrayList<>();
        LocalDate startDay = records.get(0).getRunDate();
        LocalDate endDay = records.get(records.size() - 1).getRunDate();

        while (!endDay.isBefore(startDay.with(previousOrSame(MONDAY)))) {
            dayIntervals.add(new DayInterval(startDay.with(previousOrSame(MONDAY)), startDay.with(nextOrSame(SUNDAY))));
            startDay = startDay.plusWeeks(1);
        }

        return dayIntervals;
    }

    private List<DayInterval> divideByMonths(final List<Record> records) {
        List<DayInterval> dayIntervals = new ArrayList<>();
        LocalDate startDay = records.get(0).getRunDate();
        LocalDate endDay = records.get(records.size() - 1).getRunDate();

        while (!endDay.isBefore(startDay.with(firstDayOfMonth()))) {
            dayIntervals.add(new DayInterval(startDay.with(firstDayOfMonth()), startDay.with(lastDayOfMonth())));
            startDay = startDay.plusMonths(1);
        }

        return dayIntervals;
    }

    @Getter
    @RequiredArgsConstructor
    private static class DayInterval {
        private final LocalDate start;
        private final LocalDate end;
    }

}
