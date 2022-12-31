package com.elijah.ukeme.doctorsappointmentapplication.model;

import java.time.LocalDate;
import java.util.Date;

public class PatientDto {

    private Integer id;
    private String name;
    private String email;
    private String profileImage;
    private String phoneNumber;
    private String gender;
    private String dateOfBirth;
    private String category;
    private int cardFee;
    private Integer age;
    private String createdDate;
    private String token;

    public PatientDto(Integer id, String name, String email, String profileImage, String phoneNumber, String gender, String dateOfBirth, String category, int cardFee, Integer age, String createdDate, String token) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.profileImage = profileImage;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.category = category;
        this.cardFee = cardFee;
        this.age = age;
        this.createdDate = createdDate;
        this.token = token;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getCategory() {
        return category;
    }

    public int getCardFee() {
        return cardFee;
    }

    public Integer getAge() {
        return age;
    }

    public String getToken() {
        return token;
    }
}
