package com.elijah.ukeme.doctorsappointmentapplication.model;

import java.time.LocalDate;
import java.util.Date;

public class AppointmentBookingUpdateDto {
    private String status;
    private String appointmentTime;
    private String remark;
    private String appointmentDate ;
    private String purpose ;
    private String doctorScheduled;

    public AppointmentBookingUpdateDto(String status, String appointmentTime, String remark, String appointmentDate, String purpose, String doctorScheduled) {
        this.status = status;
        this.appointmentTime = appointmentTime;
        this.remark = remark;
        this.appointmentDate = appointmentDate;
        this.purpose = purpose;
        this.doctorScheduled = doctorScheduled;
    }
}
