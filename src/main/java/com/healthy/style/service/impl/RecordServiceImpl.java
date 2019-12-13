package com.healthy.style.service.impl;

import com.healthy.style.entity.Record;
import com.healthy.style.entity.User;
import com.healthy.style.repository.RecordRepository;
import com.healthy.style.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class RecordServiceImpl implements RecordService {

    private RecordRepository recordRepository;

    @Autowired
    public void setRecordRepository(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    @Override
    public List<Record> getUserRecords(User user) {
        return recordRepository.findRecordsByUser(user);
    }

    @Override
    public List<Record> getRecordsBetweenRunDate(LocalDate from, LocalDate to) {
        return recordRepository.findRecordsByRunDateBetween(from, to);
    }

    @Override
    public List<Record> getSortedRecordsBetweenRunDate(LocalDate from, LocalDate to) {
        return recordRepository.findRecordsByRunDateBetweenOrderByRunDate(from, to);
    }

    @Override
    public List<Record> getAll() {
        return recordRepository.findAll();
    }

    @Override
    public Record getById(Long id) {
        return recordRepository.getOne(id);
    }

    @Override
    public Record save(Record entity) {
        return recordRepository.saveAndFlush(entity);
    }

    @Override
    public void delete(Long id) {
        recordRepository.deleteById(id);
    }

}
