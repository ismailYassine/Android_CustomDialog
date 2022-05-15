package com.example.trafficfinesapp_android;

import java.util.Date;

public class Offender {
    String firstName;
    String lastName;
    Integer fineAmount;
    Integer speedLimitZone;
    Integer offenderSpeed;
    Boolean isSchoolOrWorkZone;
    String fineDate;

    public Offender(String firstName, String lastName, int fineAmount, Integer speedLimitZone, Integer offenderSpeed, Boolean isSchoolOrWorkZone,
             String fineDate){
        this.firstName = firstName;
        this.lastName = lastName;
        this.fineAmount = fineAmount;
        this.speedLimitZone = speedLimitZone;
        this.offenderSpeed = offenderSpeed;
        this.isSchoolOrWorkZone = isSchoolOrWorkZone;
        this.fineDate = fineDate;
    }

    public Integer getSpeedLimitZone() {
        return speedLimitZone;
    }

    public void setSpeedLimitZone(Integer speedLimitZone) {
        this.speedLimitZone = speedLimitZone;
    }

    public Integer getOffenderSpeed() {
        return offenderSpeed;
    }

    public void setOffenderSpeed(Integer offenderSpeed) {
        this.offenderSpeed = offenderSpeed;
    }

    public Boolean getSchoolOrWorkZone() {
        return isSchoolOrWorkZone;
    }

    public void setSchoolOrWorkZone(Boolean schoolOrWorkZone) {
        isSchoolOrWorkZone = schoolOrWorkZone;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(Integer fineAmount) {
        this.fineAmount = fineAmount;
    }

    public String getTicketDate() {
        return fineDate;
    }

    public void setTicketDate(String ticketDate) {
        this.fineDate = ticketDate;
    }
}
