package com.example.freeteachbe.DTO.BodyPayload;

import java.time.LocalTime;
import java.util.Set;

public class TeacherDTO {
    private int pricePerHour;
    private String description;
    private String activeTimeStart;
    private String activeTimeEnd;
    private String activeDays;
    private Long userId;
    private Set<Long> subjectIds;

    public int getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(int pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActiveTimeStart() {
        return activeTimeStart;
    }

    public void setActiveTimeStart(String activeTimeStart) {
        this.activeTimeStart = activeTimeStart;
    }

    public String getActiveTimeEnd() {
        return activeTimeEnd;
    }

    public void setActiveTimeEnd(String activeTimeEnd) {
        this.activeTimeEnd = activeTimeEnd;
    }

    public String getActiveDays() {
        return activeDays;
    }

    public void setActiveDays(String activeDays) {
        this.activeDays = activeDays;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Set<Long> getSubjectIds() {
        return subjectIds;
    }

    public void setSubjectIds(Set<Long> subjectIds) {
        this.subjectIds = subjectIds;
    }

    public TeacherDTO(int pricePerHour, String description, String activeTimeStart, String activeTimeEnd, String activeDays, Long userId, Set<Long> subjectIds) {
        this.pricePerHour = pricePerHour;
        this.description = description;
        this.activeTimeStart = activeTimeStart;
        this.activeTimeEnd = activeTimeEnd;
        this.activeDays = activeDays;
        this.userId = userId;
        this.subjectIds = subjectIds;
    }
}
