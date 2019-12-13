package com.healthy.style.service;

import com.healthy.style.entity.Record;
import com.healthy.style.entity.User;

import java.time.LocalDate;
import java.util.List;

public interface RecordService extends EntityService<Record> {

    List<Record> getUserRecords(User user);

    List<Record> getRecordsBetweenRunDate(LocalDate from, LocalDate to);

    List<Record> getSortedRecordsBetweenRunDate(LocalDate from, LocalDate to);

}
