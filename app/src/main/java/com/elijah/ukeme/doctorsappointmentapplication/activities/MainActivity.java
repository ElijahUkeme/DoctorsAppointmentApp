package com.elijah.ukeme.doctorsappointmentapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.elijah.ukeme.doctorsappointmentapplication.R;
import com.elijah.ukeme.doctorsappointmentapplication.model.PatientDto;
import com.elijah.ukeme.doctorsappointmentapplication.model.SignInDto;
import com.elijah.ukeme.doctorsappointmentapplication.response.SignInResponse;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;

import api.BaseUrlClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import storage.SharedPreferenceManager;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText email, password;
    private TextView registrationLink;
    private Button fabLogin;
    String emailAddress,userPass;
    private boolean cancel = false;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        email = findViewById(R.id.email_login);
        password = findViewById(R.id.password_login);
        registrationLink = findViewById(R.id.textview_SignUp);
        fabLogin = findViewById(R.id.fab_login);
        progressBar = findViewById(R.id.progress_bar_main);
        registrationLink.setOnClickListener(view -> {
            toRegisterActivity();
        });
        fabLogin.setOnClickListener(view -> {
            login();
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (SharedPreferenceManager.getInstance(MainActivity.this)
        .isPatientLogin()){
            Intent intent = new Intent(MainActivity.this,ProfileActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    private void toRegisterActivity(){
        Intent intent = new Intent(MainActivity.this,PatientRegistrationActivity.class);
        startActivity(intent);
    }
    private void login() {
        emailAddress = email.getText().toString();
        userPass = password.getText().toString();
        if (emailAddress.isEmpty()) {
            email.setError("Please Enter your Email");
            cancel = true;
            email.requestFocus();
        } else if (userPass.isEmpty()) {
            password.setError("Please Enter your password");
            cancel = true;
            password.requestFocus();
        } else {
            progressBar.setVisibility(View.VISIBLE);
            SignInDto signInDto = new SignInDto(emailAddress, userPass);

            Call<PatientDto> call = BaseUrlClient
                    .getInstance().getApi().signInPatient(signInDto);
            call.enqueue(new Callback<PatientDto>() {
                @Override
                public void onResponse(Call<PatientDto> call, Response<PatientDto> response) {
                    if (response.code()==200){
                        PatientDto patientDto = response.body();
                        progressBar.setVisibility(View.GONE);
                        SharedPreferenceManager.getInstance(MainActivity.this)
                                .savePatient(patientDto);
                        Toast.makeText(MainActivity.this,"Login Successful",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }else {
                        try {
                            progressBar.setVisibility(View.GONE);
                            Log.d("Main","Error message is "+response.errorBody().string());
                            Toast.makeText(MainActivity.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                            progressBar.setVisibility(View.GONE);
                            Log.d("Main",e.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<PatientDto> call, Throwable t) {
                    Log.d("Main","Error Message from the failure "+t.getMessage());
                    Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

        }
    }
}