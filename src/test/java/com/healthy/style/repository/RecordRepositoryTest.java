package com.healthy.style.repository;

import com.healthy.style.entity.Record;
import com.healthy.style.entity.User;
import com.healthy.style.repository.annotation.RepositoryTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.Month.*;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@RepositoryTest
public class RecordRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RecordRepository recordRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;
    private Record record1;
    private Record record2;
    private Record record3;

    @Before
    public void setUp() {
        user = new User("userito", "user123", "user@email.com");

        record1 = new Record(
                LocalDate.of(2019, DECEMBER, 1),
                LocalTime.of(2, 24, 0),
                LocalTime.of(3, 58, 0), 2.3D);
        record1.setUser(user);
        entityManager.persistAndFlush(record1);

        record2 = new Record(
                LocalDate.of(2019, NOVEMBER, 20),
                LocalTime.of(5, 20, 0),
                LocalTime.of(7, 30, 59), 5.4D);
        record2.setUser(user);
        entityManager.persistAndFlush(record2);

        record3 = new Record(
                LocalDate.of(2019, DECEMBER, 15),
                LocalTime.of(15, 24, 0),
                LocalTime.of(17, 58, 0), 3.5D);
        record3.setUser(user);
        entityManager.persistAndFlush(record3);

        user.setRecords(new ArrayList<>(asList(record1, record2, record3)));
        entityManager.persistAndFlush(user);
    }

    @Test
    public void whenFindRecordsByRunDateBetween_thenReturnRecordsBetweenDates() {
        List<Record> records = recordRepository.findRecordsByRunDateBetween(
                LocalDate.of(2019, DECEMBER, 1), LocalDate.of(2020, JANUARY, 1));

        assertThat(records, hasSize(2));
        assertThat(records, hasItems(record3, record1));
    }

    @Test
    public void whenFindRecordsByRunDateBetweenOrderByRunDate_thenReturnRecordsBetweenDatesAsc() {
        List<Record> records = recordRepository.findRecordsByRunDateBetweenOrderByRunDate(
                LocalDate.of(2019, JANUARY, 1), LocalDate.of(2020, JANUARY, 1));

        assertThat(records, hasSize(3));
        assertThat(records.get(0), is(record2));
        assertThat(records.get(1), is(record1));
        assertThat(records.get(2), is(record3));
    }

    @Test
    public void whenFindRecordsByUser_thenReturnRecordsThisUser() {
        List<Record> records = recordRepository.findRecordsByUser(user);

        assertThat(records, hasSize(3));
        assertThat(records, hasItems(record1, record2, record3));
    }

    @Test
    public void whenCreateRecord_thenCreatedRecord() {
        Record expectedRecord = new Record(
                LocalDate.of(1999, JUNE, 5),
                LocalTime.of(2, 24, 0),
                LocalTime.of(3, 58, 0), 2.3D);

        Record actualRecord = entityManager.persistAndFlush(expectedRecord);

        assertThat(actualRecord.getRunDate(), is(expectedRecord.getRunDate()));
        assertThat(actualRecord.getStartTime(), is(expectedRecord.getStartTime()));
        assertThat(actualRecord.getEndTime(), is(expectedRecord.getEndTime()));
        assertThat(actualRecord.getDistance(), is(expectedRecord.getDistance()));
    }

    @Test
    public void whenCreateRecord_thenCreatedRecordWithUser() {
        Record expectedRecord = new Record(
                LocalDate.of(1999, JUNE, 5),
                LocalTime.of(2, 24, 0),
                LocalTime.of(3, 58, 0), 2.3D);
        expectedRecord.setUser(user);

        Record actualRecord = entityManager.persistAndFlush(expectedRecord);

        assertThat(actualRecord.getUser().getUsername(), is(expectedRecord.getUser().getUsername()));
    }

    @Test
    public void whenUpdateRecord_thenUpdatedRecord() {
        record1.setDistance(100D);

        Record actualRecord = entityManager.persistAndFlush(record1);

        assertThat(actualRecord.getDistance(), is(record1.getDistance()));
    }

    @Test
    public void whenDeleteRecord_thenDeletingShouldBeSuccessful() {
        recordRepository.delete(record1);

        assertThat(recordRepository.count(), is(2L));
        assertThat(userRepository.count(), is(1L));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void whenCreateRecordWithNullFields_thenThrowException() {
        recordRepository.saveAndFlush(new Record());
    }

    @After
    public void tearDown() {
        entityManager.clear();
    }

}
