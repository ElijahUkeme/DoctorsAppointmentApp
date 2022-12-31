package com.elijah.ukeme.doctorsappointmentapplication.model;

import java.time.LocalDate;
import java.util.Date;

public class AppointmentInfoDto {

    private String status;
    private Date appointmentTime;
    private LocalDate appointmentDate;
    private String purpose;
    private String doctorScheduled;
    private String phoneNumber;
    private Date createdDate;
    private String remark;

    public String getStatus() {
        return status;
    }

    public Date getAppointmentTime() {
        return appointmentTime;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public String getPurpose() {
        return purpose;
    }

    public String getDoctorScheduled() {
        return doctorScheduled;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public String getRemark() {
        return remark;
    }
}
