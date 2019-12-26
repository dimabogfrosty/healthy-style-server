package com.healthy.style.controller;

import com.healthy.style.entity.Record;
import com.healthy.style.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RecordController {

    private RecordService recordService;

    @Autowired
    public void setRecordService(final RecordService recordService) {
        this.recordService = recordService;
    }

    @GetMapping("/records")
    public List<Record> getAllRecords() {
        return recordService.getAll();
    }

    @GetMapping("/records/{id:\\d+}")
    public Record getRecordById(@PathVariable final Long id) {
        return recordService.getById(id);
    }

    @PostMapping("/records")
    public Record createRecord(@RequestBody final Record record) {
        return recordService.save(record);
    }

    @PutMapping("/records")
    public Record updateRecord(@RequestBody final Record record) {
        return recordService.save(record);
    }

    @DeleteMapping("/records/{id:\\d+}")
    public void deleteRecord(@PathVariable final Long id) {
        recordService.delete(id);
    }

}
