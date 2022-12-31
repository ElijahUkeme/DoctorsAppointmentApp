package com.elijah.ukeme.doctorsappointmentapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.elijah.ukeme.doctorsappointmentapplication.R;
import com.elijah.ukeme.doctorsappointmentapplication.model.AppointmentBooking;
import com.elijah.ukeme.doctorsappointmentapplication.model.AppointmentInfoDto;

import java.io.IOException;

import api.BaseUrlClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TokenActivity extends AppCompatActivity {
    private EditText token;
    private Button button;
    private ProgressBar progressBar;
    private String appointmentToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_token);
        token = findViewById(R.id.input_token);
        button = findViewById(R.id.next_button);
        progressBar = findViewById(R.id.progress_bar_token);
        //appointmentToken = token.getText().toString();
        appointmentToken = "elidrbooking4efde4c4-cc69-4736-b84a-0feba0ee48a8";

        button.setOnClickListener(view -> {
            //getAppointmentInformation();
        });
    }

//    private void getAppointmentInformation(){
//        progressBar.setVisibility(View.VISIBLE);
//        Call<AppointmentBooking> call = BaseUrlClient
//                .getInstance().getApi().getAppointmentInfo(appointmentToken);
//        call.enqueue(new Callback<AppointmentBooking>() {
//            @Override
//            public void onResponse(Call<AppointmentBooking> call, Response<AppointmentBooking> response) {
//                if (response.code()==200){
//                    progressBar.setVisibility(View.GONE);
//
//                    Log.d("Main",response.body().getDoctorScheduled());
//                    Log.d("Main",response.body().getStatus());
//                    Log.d("Main",response.body().getRemark());
//                    Log.d("Main",response.body().getAppointmentDate().toString());
//                    Log.d("Main",response.body().getAppointmentTime().toString());
//                    Toast.makeText(TokenActivity.this, "Appointment Booked Successfully", Toast.LENGTH_SHORT).show();
//                }else {
//                    progressBar.setVisibility(View.GONE);
//                    Log.d("Main","The response code is "+response.code());
//                    Log.d("Main","Error response is "+response.errorBody());
//                    Toast.makeText(TokenActivity.this, "Error Occurred, please try Again", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<AppointmentBooking> call, Throwable t) {
//                progressBar.setVisibility(View.GONE);
//                Log.d("Main","Error from the server is "+t.getMessage());
//                Toast.makeText(TokenActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}