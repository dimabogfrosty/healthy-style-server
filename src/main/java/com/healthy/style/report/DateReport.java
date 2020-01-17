package com.healthy.style.report;

import com.healthy.style.entity.Record;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class DateReport extends Report {

    private final LocalDate startDay;
    private final LocalDate endDay;

    public DateReport(List<Record> records, LocalDate startDay, LocalDate endDay) {
        super(records);
        this.startDay = startDay;
        this.endDay = endDay;
    }

}
