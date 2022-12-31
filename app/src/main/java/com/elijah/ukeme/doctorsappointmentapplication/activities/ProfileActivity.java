package com.elijah.ukeme.doctorsappointmentapplication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.elijah.ukeme.doctorsappointmentapplication.R;
import com.elijah.ukeme.doctorsappointmentapplication.model.PatientDto;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

import de.hdodenhof.circleimageview.CircleImageView;
import storage.SharedPreferenceManager;

public class ProfileActivity extends AppCompatActivity {

    private CircleImageView profileImage;
    private TextView name, email,phone,dateOfBirth,category,gender,age,createdDate;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileImage = findViewById(R.id.image_profile);
        name = findViewById(R.id.name_profile);
        email = findViewById(R.id.profile_email);
        phone = findViewById(R.id.phone_profile);
        dateOfBirth = findViewById(R.id.profile_date_of_birth);
        category = findViewById(R.id.profile_category);
        gender = findViewById(R.id.profile_gender);
        age = findViewById(R.id.profile_age);
        createdDate = findViewById(R.id.profile_created_date);
        toolbar = findViewById(R.id.toolBar);
        toolbar.setTitle("Personal Profile");
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(view -> {
            finish();
        });

        PatientDto patientDto = SharedPreferenceManager.getInstance(ProfileActivity.this)
                .getPatient();
        displayUserDetails(patientDto.getName(),patientDto.getEmail(),patientDto.getProfileImage(),
                patientDto.getPhoneNumber(),patientDto.getGender(),patientDto.getDateOfBirth(),patientDto.getCategory(),
                patientDto.getAge(),patientDto.getCreatedDate());
        Log.d("Main","From the Profile Activity");
        Log.d("Main","user id is "+patientDto.getId());
        Log.d("Main","Username "+patientDto.getName());
        Log.d("Main","Email "+patientDto.getEmail());
        Log.d("Main","Profile Image "+patientDto.getProfileImage());
        Log.d("Main","Phone Number "+patientDto.getPhoneNumber());
        Log.d("Main","gender "+patientDto.getGender());
        Log.d("Main","Date of Birth "+patientDto.getDateOfBirth());
        Log.d("Main","Category "+patientDto.getCategory());
        Log.d("Main","Age "+patientDto.getAge());
        Log.d("Main","CardFee "+patientDto.getCardFee());
        Log.d("Main","Token "+patientDto.getToken());
        Log.d("Main","Created Date "+patientDto.getCreatedDate());
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!SharedPreferenceManager.getInstance(ProfileActivity.this)
        .isPatientLogin()){
            Intent intent = new Intent(ProfileActivity.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
    private void displayUserDetails(String userName, String userEmail,String userImage,String userPhone,String userGender,
                                    String userDob,String userCategory, int userAge,String userCreatedDate){

//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        simpleDateFormat.format(userCreatedDate);

        name.setText(userName);
        email.setText(userEmail);
        Picasso.get().load(userImage).into(profileImage);
        phone.setText(userPhone);
        gender.setText(userGender);
        dateOfBirth.setText(userDob);
        category.setText(userCategory);
        age.setText(String.valueOf(userAge));
        createdDate.setText(userCreatedDate);
    }


}