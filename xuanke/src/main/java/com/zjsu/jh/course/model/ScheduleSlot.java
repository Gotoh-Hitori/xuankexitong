package com.zjsu.jh.course.model;

public class ScheduleSlot {
    private String dayOfWeek;        // MONDAY / TUESDAY / ...
    private String startTime;        // "08:00"
    private String endTime;          // "10:00"
    private int expectedAttendance;  // 预期人数

    public ScheduleSlot() {}

    public ScheduleSlot(String dayOfWeek, String startTime, String endTime, int expectedAttendance) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.expectedAttendance = expectedAttendance;
    }

    // Getters & Setters
    public String getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(String dayOfWeek) { this.dayOfWeek = dayOfWeek; }
    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }
    public String getEndTime() { return endTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }
    public int getExpectedAttendance() { return expectedAttendance; }
    public void setExpectedAttendance(int expectedAttendance) { this.expectedAttendance = expectedAttendance; }
}
