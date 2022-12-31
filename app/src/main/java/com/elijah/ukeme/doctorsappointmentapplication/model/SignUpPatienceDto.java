package com.elijah.ukeme.doctorsappointmentapplication.model;


public class SignUpPatienceDto {



    private String name;
    private String email;
    private String password;
    private String gender;
    private String profileImage;
    private String phoneNumber;
    private String dateOfBirth;

    public SignUpPatienceDto(String name, String email, String password, String gender, String profileImage, String phoneNumber, String dateOfBirth) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.profileImage = profileImage;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
    }
}
