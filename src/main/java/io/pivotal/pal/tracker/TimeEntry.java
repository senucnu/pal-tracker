package io.pivotal.pal.tracker;

import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.Objects;

@ResponseBody
public class TimeEntry {
    private long id;
    private long projectId;
    private long userId;
    private LocalDate date;
    private int hours;

    public TimeEntry() {

    }

    public TimeEntry(long projectId, long userId, LocalDate date, int hours) {
        this.projectId = projectId;
        this.userId = userId;
        this.date = date;
        this.hours = hours;
    }

    public TimeEntry(long timeEntryId, long projectId, long userId, LocalDate date, int hours) {
        this.id = timeEntryId;
        this.projectId = projectId;
        this.userId = userId;
        this.date = date;
        this.hours = hours;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimeEntry)) return false;
        TimeEntry timeEntry = (TimeEntry) o;
        return  getProjectId() == timeEntry.getProjectId() &&
                getUserId() == timeEntry.getUserId() &&
                getHours() == timeEntry.getHours() &&
                Objects.equals(getDate(), timeEntry.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getProjectId(), getUserId(), getDate(), getHours());
    }
}
