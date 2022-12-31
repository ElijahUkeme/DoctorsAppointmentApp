package com.elijah.ukeme.doctorsappointmentapplication.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.elijah.ukeme.doctorsappointmentapplication.R;
import com.elijah.ukeme.doctorsappointmentapplication.model.AppointmentBookingDto;
import com.elijah.ukeme.doctorsappointmentapplication.response.ApiResponse;
import com.google.android.material.textfield.TextInputEditText;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import api.BaseUrlClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookAppointmentActivity extends AppCompatActivity {

    private TextInputEditText token, purpose;
    private Button btnBook, btnDate, btnTime;
    private boolean cancel = false;
    private ProgressBar progressBar;
    private String date, time,userToken;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        token = findViewById(R.id.editText_token);
        purpose = findViewById(R.id.editText_purpose);
        btnBook = findViewById(R.id.btn_book_appointment);
        btnDate = findViewById(R.id.btn_date_of_appointment);
        btnTime = findViewById(R.id.btn_time_of_appointment);
        progressBar = findViewById(R.id.progress_bar);
        //userToken = token.getText().toString();
        userToken = "816a7577-b57b-43b6-bc9d-ed1286e66801";

        btnBook.setOnClickListener(view -> {
            try {
                validateInputs();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        btnDate.setOnClickListener(view -> {
            setDatePicker((Button) view);
        });

        btnTime.setOnClickListener(view -> {
            setTimePicker((Button) view);
        });
    }

    private void setDatePicker(final Button button) {
        Calendar current_calendar = Calendar.getInstance();
        DatePickerDialog dialog = DatePickerDialog.newInstance((view, year, monthOfYear, dayOfMonth) -> {

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, monthOfYear);
                    calendar.set(Calendar.DATE, dayOfMonth);
                    long date_millis = calendar.getTimeInMillis();
                    button.setText(getFormattedDateEvent(date_millis));

                },
                current_calendar.get(Calendar.YEAR),
                current_calendar.get(Calendar.MONTH),
                current_calendar.get(Calendar.DATE));
        dialog.setThemeDark(false);
        dialog.setAccentColor(getResources().getColor(R.color.cyan_800_overlay));
        dialog.setMinDate(current_calendar);
        dialog.show(getFragmentManager(), "Date PickerDialog");
    }

    public static String getFormattedDateEvent(Long dateTime) {
        SimpleDateFormat newFormat = new SimpleDateFormat("dd-MMM-yyyy");
        return newFormat.format(new Date(dateTime));
    }

    private void setTimePicker(final Button button) {
        Calendar cur_calendar = Calendar.getInstance();
        TimePickerDialog pickerDialog = TimePickerDialog.newInstance((view, hourOfDay, minute, second) -> {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calendar.set(Calendar.MINUTE, minute);
                    calendar.set(Calendar.AM_PM, calendar.get(Calendar.AM_PM));
                    long time_millis = calendar.getTimeInMillis();
                    button.setText(getFormattedTimeEvent(time_millis));

                },
                cur_calendar.get(Calendar.HOUR_OF_DAY), cur_calendar.get(Calendar.MINUTE), true);
        pickerDialog.setThemeDark(false);
        pickerDialog.setAccentColor(getResources().getColor(R.color.cyan_800_overlay));
        pickerDialog.show(getFragmentManager(), "DialogTimePicker");
    }

    public static String getFormattedTimeEvent(Long time) {
        SimpleDateFormat newFormat = new SimpleDateFormat("h:mm a");
        return newFormat.format(new Date(time));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void validateInputs() throws ParseException {
        if (token.getText().toString().isEmpty()) {
            token.setError("Please paste your token here");
            cancel = true;
            token.requestFocus();
        } else if (purpose.getText().toString().isEmpty()) {
            purpose.setError("Please Enter the purpose of your Appointment");
            cancel = true;
            purpose.requestFocus();
        } else if (btnDate.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(BookAppointmentActivity.this, "Please Select the Appointment Date", Toast.LENGTH_SHORT).show();
        } else if (btnTime.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(BookAppointmentActivity.this, "Please Select the Appointment Time", Toast.LENGTH_SHORT).show();
        } else {
            bookAppointment();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void bookAppointment() throws ParseException {
        progressBar.setVisibility(View.VISIBLE);
        date = btnDate.getText().toString();
        time = btnTime.getText().toString();

        AppointmentBookingDto appointmentBookingDto = new AppointmentBookingDto(time,date,purpose.getText().toString());

        Call<ApiResponse> call = BaseUrlClient
                .getInstance().getApi().bookAppointment(
                        appointmentBookingDto, userToken
                );
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.code() == 201) {
                    progressBar.setVisibility(View.GONE);
                    Log.d("Main",response.body().getMessage());
                    Toast.makeText(BookAppointmentActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(BookAppointmentActivity.this,"Check your email to get the appointment booking token", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        progressBar.setVisibility(View.GONE);
                        Log.d("Main", "Response code is " + response.code());
                        Log.d("Main","Error from server log "+response.errorBody().string());

                    }catch (Exception e){
                        progressBar.setVisibility(View.GONE);
                        e.printStackTrace();

                    }

                }

            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.d("Main", "Failure message is " + t.getMessage());
                Toast.makeText(BookAppointmentActivity.this, "Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public Date formatTimeFromString(String time) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("hh:mm");
            Date dateTime = format.parse(time);
            System.out.println(dateTime);

        return dateTime;
    }

    public Date getAppointmentTime() throws ParseException {
        Date appointmentTime= new Date("12:30");
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String theDate = dateFormat.format(appointmentTime);
        appointmentTime = dateFormat.parse(theDate);
        return appointmentTime;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private LocalDate getFormattedDate(String date){
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate  d1 = LocalDate.parse(date, df);
        return d1;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private LocalDate getDat(String date){
        DateTimeFormatter df = new DateTimeFormatterBuilder()
                // case insensitive to parse JAN and FEB
                .parseCaseInsensitive()
                // add pattern
                .appendPattern("dd-MMM-yyyy")
                // create formatter (use English Locale to parse month names)
                .toFormatter(Locale.ENGLISH);
        return LocalDate.parse(date,df);
    }
}