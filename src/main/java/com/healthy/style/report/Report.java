package com.healthy.style.report;

import com.healthy.style.entity.Record;
import lombok.RequiredArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@RequiredArgsConstructor
public class Report {

    private final List<Record> records;

    public LocalTime getAverageTime() {
        return LocalTime.ofNanoOfDay(getTotalTime().toNanoOfDay() / records.size());
    }

    public LocalTime getTotalTime() {
        long nanoSum = records.stream()
                .map(record -> record.getRunningTime().toNanoOfDay())
                .reduce(Long::sum)
                .orElse(0L);
        return LocalTime.ofNanoOfDay(nanoSum);
    }

    public LocalTime getMaxTime() {
        long nanoTime = records.stream()
                .map(record -> record.getRunningTime().toNanoOfDay())
                .max(Long::compareTo)
                .orElse(0L);
        return LocalTime.ofNanoOfDay(nanoTime);
    }

    public double getAverageDistance() {
        double averageDistance = getTotalDistance() / records.size();
        return (double) Math.round(averageDistance * 10) / 10;
    }

    public double getTotalDistance() {
        double totalDistance = records.stream()
                .map(Record::getDistance)
                .reduce(Double::sum)
                .orElse(0d);
        return (double) Math.round(totalDistance * 10) / 10;
    }

    public double getMaxDistance() {
        return records.stream()
                .map(Record::getDistance)
                .max(Double::compareTo)
                .orElse(0d);
    }

    public double getAverageSpeed() {
        double averageSpeed = getAverageDistance() / (getAverageTime().toSecondOfDay() / 3600d);
        return (double) Math.round(averageSpeed * 100) / 100;
    }

    public double getMaxSpeed() {
        double maxSpeed = records.stream()
                .map(record -> record.getDistance() / (record.getRunningTime().toSecondOfDay() / 3600d))
                .max(Double::compareTo).orElse(0d);
        return (double) Math.round(maxSpeed * 100) / 100;
    }
}
