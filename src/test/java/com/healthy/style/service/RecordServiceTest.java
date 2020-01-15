package com.healthy.style.service;

import com.healthy.style.entity.Record;
import com.healthy.style.entity.User;
import com.healthy.style.repository.RecordRepository;
import com.healthy.style.service.impl.RecordServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.Month.DECEMBER;
import static java.time.Month.NOVEMBER;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RecordServiceTest {

    @Mock
    private RecordRepository recordRepository;

    @InjectMocks
    private RecordServiceImpl recordService;

    private User user;
    private Record record1;
    private Record record2;
    private Record record3;

    @Before
    public void setUp() {
        user = new User();
        user.setUsername("userito");
        user.setPassword("user123");
        user.setEmail("user@email.com");

        record1 = new Record();
        record1.setRunDate(LocalDate.of(2019, DECEMBER, 1));
        record1.setStartTime(LocalTime.of(2, 24, 0));
        record1.setEndTime(LocalTime.of(3, 58, 0));
        record1.setDistance(2.3D);
        record1.setUser(user);
        record1.setId(1L);

        record2 = new Record();
        record2.setRunDate(LocalDate.of(2019, NOVEMBER, 20));
        record2.setStartTime(LocalTime.of(5, 20, 0));
        record2.setEndTime(LocalTime.of(7, 30, 59));
        record2.setDistance(5.4D);
        record2.setUser(user);
        record2.setId(2L);

        record3 = new Record();
        record3.setRunDate(LocalDate.of(2019, DECEMBER, 15));
        record3.setStartTime(LocalTime.of(15, 24, 0));
        record3.setEndTime(LocalTime.of(17, 58, 0));
        record3.setDistance(3.5D);
        record3.setUser(user);
        record3.setId(3L);

        user.setRecords(new ArrayList<>(asList(record1, record2, record3)));
    }

    @Test
    public void whenGetUserRecords_thenReturnRecords() {
        when(recordRepository.findRecordsByUser(user)).thenReturn(asList(record1, record2, record3));

        List<Record> records = recordService.getUserRecords(user);

        assertThat(records, hasSize(3));
        assertThat(records, hasItems(record1, record2, record3));
        verify(recordRepository, times(1)).findRecordsByUser(user);
    }

    @Test
    public void whenGetRecordsBetweenRunDate_thenReturnRecords() {
        LocalDate from = LocalDate.of(2019, DECEMBER, 1);
        LocalDate to = LocalDate.of(2019, DECEMBER, 30);

        when(recordRepository.findRecordsByRunDateBetween(from, to)).thenReturn(asList(record1, record3));

        List<Record> records = recordService.getRecordsBetweenRunDate(from, to);

        assertThat(records, hasSize(2));
        assertThat(records, hasItems(record1, record3));
        verify(recordRepository, times(1)).findRecordsByRunDateBetween(from, to);
    }

    @Test
    public void whenGetSortedRecordsBetweenRunDate_thenReturnRecords() {
        LocalDate from = LocalDate.of(2019, NOVEMBER, 1);
        LocalDate to = LocalDate.of(2019, DECEMBER, 30);

        when(recordRepository.findRecordsByRunDateBetweenOrderByRunDate(from, to))
                .thenReturn(asList(record1, record2, record3));

        List<Record> records = recordService.getSortedRecordsBetweenRunDate(from, to);

        assertThat(records, hasSize(3));
        assertThat(records, hasItems(record1, record2, record3));
        verify(recordRepository, times(1)).findRecordsByRunDateBetweenOrderByRunDate(from, to);
    }

    @Test
    public void whenGetAll_thenReturnRecords() {
        when(recordRepository.findAll()).thenReturn(asList(record1, record2, record3));

        List<Record> records = recordService.getAll();

        assertThat(records, hasSize(3));
        assertThat(records, hasItems(record1, record2, record3));
        verify(recordRepository, times(1)).findAll();
    }

    @Test
    public void whenGetById_thenReturnRecord() {
        when(recordRepository.getOne(record1.getId())).thenReturn(record1);

        Record actualRecord = recordService.getById(record1.getId());

        assertThat(actualRecord.getId(), is(record1.getId()));
        verify(recordRepository, times(1)).getOne(record1.getId());
    }

    @Test
    public void whenSaveRecord_thenReturnRecord() {
        when(recordRepository.saveAndFlush(any(Record.class))).thenReturn(new Record());

        Record actualRecord = recordService.save(record1);

        assertThat(actualRecord, instanceOf(Record.class));
        verify(recordRepository, times(1)).saveAndFlush(record1);
    }

    @Test
    public void whenDeleteRecord_thenDeletingShouldBeSuccessful() {
        recordService.delete(record1.getId());

        verify(recordRepository, times(1)).deleteById(record1.getId());
    }
}
