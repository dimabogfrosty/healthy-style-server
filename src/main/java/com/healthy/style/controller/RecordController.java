package com.healthy.style.controller;

import com.healthy.style.entity.Record;
import com.healthy.style.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;

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
