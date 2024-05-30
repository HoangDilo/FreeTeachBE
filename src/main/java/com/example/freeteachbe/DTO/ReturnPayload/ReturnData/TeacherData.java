package com.example.freeteachbe.DTO.ReturnPayload.ReturnData;

import com.example.freeteachbe.Entity.SubjectEntity;

import java.time.LocalTime;
import java.util.Set;

public class TeacherData extends UserData {
    private int pricePerHour;
    private String description;
    private String activeTimeStart;
    private String activeTimeEnd;
    private String activeDays;
    private double averagePoint;


    public TeacherData(Long id, String name, String email, String avatarURL, String username, int money, int pricePerHour, String description, String activeTimeStart, String activeTimeEnd, String activeDays, double averagePoint) {
        super(id, name, email, avatarURL, username, money);
        this.pricePerHour = pricePerHour;
        this.description = description;
        this.activeTimeStart = activeTimeStart;
        this.activeTimeEnd = activeTimeEnd;
        this.activeDays = activeDays;
        this.averagePoint = averagePoint;
    }

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

    public double getAveragePoint() {
        return averagePoint;
    }

    public void setAveragePoint(double averagePoint) {
        this.averagePoint = averagePoint;
    }
}
