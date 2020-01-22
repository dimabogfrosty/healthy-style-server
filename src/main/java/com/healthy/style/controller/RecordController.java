package com.healthy.style.controller;

import com.healthy.style.entity.Record;
import com.healthy.style.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

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
    public ResponseEntity<?> getRecordById(@PathVariable final Long id) {
        Optional<Record> record = recordService.getById(id);
        return record.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(NOT_FOUND));
    }

    @PostMapping("/records")
    public ResponseEntity<Record> createRecord(@Valid @RequestBody final Record record) throws URISyntaxException {
        Record createdRecord = recordService.save(record);
        return ResponseEntity.created(new URI("api/records/" + createdRecord.getId())).body(createdRecord);
    }

    @PutMapping("/records")
    public ResponseEntity<Record> updateRecord(@Valid @RequestBody final Record record) {
        Record updatedRecord = recordService.save(record);
        return ResponseEntity.ok().body(updatedRecord);
    }

    @DeleteMapping("/records/{id:\\d+}")
    public ResponseEntity<?> deleteRecord(@PathVariable final Long id) {
        recordService.delete(id);
        return ResponseEntity.ok().build();
    }

}
