package com.healthy.style.service.impl;

import com.healthy.style.entity.Record;
import com.healthy.style.repository.RecordRepository;
import com.healthy.style.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RecordServiceImpl implements RecordService {

    private final RecordRepository recordRepository;

    @Override
    public List<Record> getUserRecords(final Long id) {
        return recordRepository.findRecordsByUserIdOrderByRunDate(id);
    }

    @Override
    public List<Record> getRecordsBetweenRunDate(final LocalDate from, final LocalDate to) {
        return recordRepository.findRecordsByRunDateBetween(from, to);
    }

    @Override
    public List<Record> getSortedRecordsBetweenRunDate(final LocalDate from, final LocalDate to) {
        return recordRepository.findRecordsByRunDateBetweenOrderByRunDate(from, to);
    }

    @Override
    public List<Record> getAll() {
        return recordRepository.findAll();
    }

    @Override
    public Optional<Record> getById(final Long id) {
        return recordRepository.findById(id);
    }

    @Override
    public Record save(final Record entity) {
        return recordRepository.saveAndFlush(entity);
    }

    @Override
    public void delete(final Long id) {
        recordRepository.deleteById(id);
    }

}
