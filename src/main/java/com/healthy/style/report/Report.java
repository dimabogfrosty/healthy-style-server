package com.healthy.style.report;

import com.healthy.style.entity.Record;

import java.time.LocalTime;
import java.util.List;

public class Report {

    private final List<Record> records;

    public Report(final List<Record> records) {
        this.records = records;
    }

    public LocalTime getAverageTime() {
        return LocalTime.ofNanoOfDay(getTotalTime().toNanoOfDay() / records.size());
    }

    public LocalTime getTotalTime() {
        long nanoSum = 0;
        for (Record record : records) {
            nanoSum += record.getRunningTime().toNanoOfDay();
        }
        return LocalTime.ofNanoOfDay(nanoSum);
    }

    public LocalTime getMaxTime() {
        long nanoTime = 0;
        for (Record record : records) {
            if (nanoTime < record.getRunningTime().toNanoOfDay()) {
                nanoTime = record.getRunningTime().toNanoOfDay();
            }
        }
        return LocalTime.ofNanoOfDay(nanoTime);
    }

    public double getAverageDistance() {
        double averageDistance = getTotalDistance() / records.size();
        return (double) Math.round(averageDistance * 10) / 10;
    }

    public double getTotalDistance() {
        double totalDistance = 0;
        for (Record record : records) {
            totalDistance += record.getDistance();
        }
        return (double) Math.round(totalDistance * 10) / 10;
    }

    public double getMaxDistance() {
        double maxDistance = 0;
        for (Record record : records) {
            if (maxDistance < record.getDistance()) {
                maxDistance = record.getDistance();
            }
        }
        return maxDistance;
    }

    public double getAverageSpeed() {
        double averageSpeed = getAverageDistance() / (getAverageTime().toSecondOfDay() / 3600d);
        return (double) Math.round(averageSpeed * 100) / 100;
    }

    public double getMaxSpeed() {
        double maxSpeed = 0;
        for (Record record : records) {
            double tempSpeed = record.getDistance() / (record.getRunningTime().toSecondOfDay() / 3600d);
            if (maxSpeed < tempSpeed) {
                maxSpeed = tempSpeed;
            }
        }
        return (double) Math.round(maxSpeed * 100) / 100;
    }
}
