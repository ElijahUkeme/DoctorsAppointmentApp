package com.elijah.ukeme.doctorsappointmentapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.elijah.ukeme.doctorsappointmentapplication.R;
import com.elijah.ukeme.doctorsappointmentapplication.model.AppointmentBooking;
import com.elijah.ukeme.doctorsappointmentapplication.model.AppointmentInfoDto;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;

import api.BaseUrlClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentInformationActivity extends AppCompatActivity {

    private TextView date,time,purpose,remark,doctorScheduled,status,createdDateTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_information);

        date = findViewById(R.id.info_date);
        time = findViewById(R.id.info_time);
        purpose = findViewById(R.id.info_purpose);
        remark = findViewById(R.id.info_remark);
        status = findViewById(R.id.info_status);
        createdDateTV = findViewById(R.id.info_createdDate);
        doctorScheduled = findViewById(R.id.info_doctor_scheduled);
        date.setOnClickListener(view -> {
            getAppointmentInfo("elidrbooking4efde4c4-cc69-4736-b84a-0feba0ee48a8");
        });

    }
    private void getAppointmentInfo(String token){
        Call<JsonElement> call = BaseUrlClient
                .getInstance().getApi().getAppointmentInfo(token);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.code()==200){
                    try {
                        assert response.body() != null;

                        JsonObject jsonObject = response.body().getAsJsonObject();
                        String theStatus = jsonObject.get("status").getAsString();
                        String theTime = jsonObject.get("appointmentTime").getAsString();
                        String theDate = jsonObject.get("appointmentDate").getAsString();
                        String thePurpose = jsonObject.get("purpose").getAsString();
                        String theDoctor = jsonObject.get("doctorScheduled").getAsString();
                        String createdDate = jsonObject.get("createdDate").getAsString();
                        String theRemark = jsonObject.get("remark").getAsString();
                        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                        date.setText(theDate);
                        time.setText(theTime);
                        purpose.setText(thePurpose);
                        remark.setText(theRemark);
                        doctorScheduled.setText(theDoctor);
                        status.setText(theStatus);
                        createdDateTV.setText(createdDate);
                    }catch (Exception e){
                        Log.d("Main","There was this error "+e.getMessage());
                        Log.d("Main","Response code "+response.body());

                    }
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.d("Main","Error from the response "+t.getMessage());
            }
        });
    }
}