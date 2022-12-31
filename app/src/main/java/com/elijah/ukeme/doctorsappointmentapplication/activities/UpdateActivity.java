package com.elijah.ukeme.doctorsappointmentapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.elijah.ukeme.doctorsappointmentapplication.R;
import com.elijah.ukeme.doctorsappointmentapplication.model.AppointmentBookingDto;
import com.elijah.ukeme.doctorsappointmentapplication.model.AppointmentBookingUpdateDto;
import com.elijah.ukeme.doctorsappointmentapplication.response.ApiResponse;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;

import api.BaseUrlClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UpdateActivity extends AppCompatActivity {

    private TextInputEditText status, appointmentTime,appointmentDate,remark,doctorScheduled,purpose;
    private Button updateBtn;
    String token = "";
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        status = findViewById(R.id.update_status);
        appointmentDate = findViewById(R.id.update_date);
        appointmentTime = findViewById(R.id.update_time);
        remark = findViewById(R.id.update_remark);
        doctorScheduled = findViewById(R.id.update_doctor_scheduled);
        updateBtn = findViewById(R.id.btn_update_appointment);
        purpose = findViewById(R.id.update_purpose);
        progressBar = findViewById(R.id.progress_bar_update);

        updateBtn.setOnClickListener(view -> {
            updateAppointment();
        });

    }
    private void updateAppointment(){
        String appointmentStatus = status.getText().toString();
        String time = appointmentTime.getText().toString();
        String date = appointmentDate.getText().toString();
        String appointmentRemark = remark.getText().toString();
        String scheduledDoctor = doctorScheduled.getText().toString();
        String appointmentPurpose = purpose.getText().toString();
        AppointmentBookingUpdateDto updateDto =new AppointmentBookingUpdateDto(appointmentStatus,
                time,appointmentRemark,date,appointmentPurpose,scheduledDoctor);

        Call<ApiResponse> call = BaseUrlClient
                .getInstance().getApi()
                .updateAppointment(updateDto,token);
        progressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.code()==200){
                    progressBar.setVisibility(View.GONE);
                    Log.d("Main","The response is "+response.body().getMessage());
                    Toast.makeText(UpdateActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }else {
                    try {
                        Log.d("Main","The error is "+response.errorBody().string());
                        Log.d("Main","Response code is "+response.code());
                        progressBar.setVisibility(View.GONE);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.d("Main","Error from the server is "+t.getMessage());
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}