package com.elijah.ukeme.doctorsappointmentapplication.model;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.util.Date;

public class AppointmentBookingDto {

    private String appointmentTime;
    private String appointmentDate;
    private String purpose;

    public AppointmentBookingDto(String appointmentTime, String appointmentDate, String purpose) {
        this.appointmentTime = appointmentTime;
        this.appointmentDate = appointmentDate;
        this.purpose = purpose;
    }
}
