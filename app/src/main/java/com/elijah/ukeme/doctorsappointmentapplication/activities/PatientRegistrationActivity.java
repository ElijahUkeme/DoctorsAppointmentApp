package com.elijah.ukeme.doctorsappointmentapplication.activities;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.elijah.ukeme.doctorsappointmentapplication.R;
import com.elijah.ukeme.doctorsappointmentapplication.model.ImageDto;
import com.elijah.ukeme.doctorsappointmentapplication.model.SignUpPatienceDto;
import com.elijah.ukeme.doctorsappointmentapplication.response.ApiResponse;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import api.BaseUrlClient;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientRegistrationActivity extends AppCompatActivity {

    private CircleImageView profilePicture;
    private TextInputEditText name, email,password,phone;
    private Button btnDateOfBirth;
    private TextView imagePicker,personalDetails;
    private ImageView saveButton, closeButton;
    private RadioGroup radioGroup;
    private RadioButton male,female;
    private String gender = "";
    private boolean cancel = false, genderChecked=false;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri profileImageUri;
    String dateTime,selectedGender,profileImagePath;
    private String userName,userEmail,userPassword,phoneNumber;
    private ProgressBar loadingPB;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_registration);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        loadingPB = findViewById(R.id.idPBLoading);
        profilePicture = findViewById(R.id.profile_image);
        name = findViewById(R.id.editText_name);
        email = findViewById(R.id.editText_email);
        password = findViewById(R.id.editText_password);
        btnDateOfBirth = findViewById(R.id.btn_date_of_birth);
        imagePicker = findViewById(R.id.textview_profile_image_picker);
        saveButton = findViewById(R.id.save_button);
        closeButton = findViewById(R.id.close_button);
        radioGroup = findViewById(R.id.gender);
        male = findViewById(R.id.radio_male);
        personalDetails = findViewById(R.id.personal_details);
        female = findViewById(R.id.radio_female);
        phone = findViewById(R.id.editText_phoneNumber);
        male.setChecked(false);
        female.setChecked(false);

        btnDateOfBirth.setOnClickListener(view -> {
            setDate();
        });
        personalDetails.setOnClickListener(view -> {
            getAllPatients();
        });
        imagePicker.setOnClickListener(view -> {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,PICK_IMAGE_REQUEST);
        });
        saveButton.setOnClickListener(view -> {
            validateInfo();
        });
        closeButton.setOnClickListener(view -> {
            close();
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode== PICK_IMAGE_REQUEST && resultCode== RESULT_OK ){

            profileImageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),profileImageUri);
                profilePicture.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            profileImagePath = profileImageUri.toString();
        }
    }
    private void validateInfo(){
        userName = name.getText().toString();
        userEmail = email.getText().toString();
        userPassword = password.getText().toString();
        phoneNumber = phone.getText().toString();
        if (male.isChecked()){
            genderChecked = true;
            selectedGender = male.getText().toString();
        }else if (female.isChecked()){
            genderChecked = true;
            selectedGender = female.getText().toString();
        }else {
            genderChecked = false;
        }

        dateTime = btnDateOfBirth.getText().toString();
        if (userName.isEmpty()){
            name.setError("Please Enter Your Name");
            cancel = true;
            name.requestFocus();
        }else if (userEmail.isEmpty()){
            email.setError("Please Enter Your Email Address");
            cancel = true;
            email.requestFocus();
        }else if (userPassword.isEmpty()) {
            password.setError("Please Enter Your Password");
            cancel = true;
            password.requestFocus();
        }else if (phoneNumber.isEmpty()){
                password.setError("Please Enter Your Phone Number");
                cancel = true;
                phone.requestFocus();
        }else if (!genderChecked){
            Toast.makeText(PatientRegistrationActivity.this,"Please Select Your Gender",Toast.LENGTH_SHORT).show();
        }else if (dateTime.equalsIgnoreCase("Select here")){
            Toast.makeText(PatientRegistrationActivity.this,"Please Select Your Date of Birth",Toast.LENGTH_SHORT).show();
        }else if (profileImageUri ==null){
            Toast.makeText(PatientRegistrationActivity.this,"Please pick a Profile Image from your Gallery",Toast.LENGTH_SHORT).show();
        }else {
            try {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    uploadProfileImage();
                }
            }catch (Exception exception){
                exception.printStackTrace();
                Log.d("Main",exception.getMessage());
                Toast.makeText(PatientRegistrationActivity.this, "Error "+exception.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void close(){
        Intent intent = new Intent(PatientRegistrationActivity.this,MainActivity.class);
        startActivity(intent);
    }
    private void setDate(){

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        android.app.DatePickerDialog dialog = new DatePickerDialog(
                PatientRegistrationActivity.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mDateSetListener,
                year,month,day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d("Main", "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = year + "-" + (month<10?"0":"") + month + "-" + (day<10?"0":"") + day;
                btnDateOfBirth.setText(date);
            }
        };
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createPatient(String profileImageUrl) {
        loadingPB.setVisibility(View.VISIBLE);

        SignUpPatienceDto signUpPatienceDto = new SignUpPatienceDto(userName,userEmail,userPassword,
                selectedGender,profileImageUrl,phoneNumber, dateTime);

        Call<ApiResponse> call = BaseUrlClient.getInstance()
                .getApi().createPatient(signUpPatienceDto);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.code()==201){
                    loadingPB.setVisibility(View.GONE);
                    String message = response.body().getMessage();
                    Log.d("Main",message);
                    Toast.makeText(PatientRegistrationActivity.this, message, Toast.LENGTH_SHORT).show();
                }else {
                    try {
                       String error= response.errorBody().string();
                       loadingPB.setVisibility(View.GONE);
                       Log.d("Main","Error message "+error);
                        Toast.makeText(PatientRegistrationActivity.this, error, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        loadingPB.setVisibility(View.GONE);
                        Log.d("Main","Error message "+e.getMessage());
                        Toast.makeText(PatientRegistrationActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                loadingPB.setVisibility(View.GONE);
                Log.d("Main",t.getMessage());
                Toast.makeText(PatientRegistrationActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void uploadProfileImage(){
        try {
            File file = new File(getDriveFile(PatientRegistrationActivity.this,profileImageUri));
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file",file.getName(),requestBody);

        Call<ImageDto> imageDtoCall = BaseUrlClient
                .getInstance().getApi().uploadProfileImage(body);
        imageDtoCall.enqueue(new Callback<ImageDto>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<ImageDto> call, Response<ImageDto> response) {
                if (response.code()==201){
                    assert response.body() != null;
                    Log.d("Main","The image url is "+response.body().getDownloadUrl());
                    Log.d("Main","The file size is "+response.body().getFileSize());
                    createPatient(response.body().getDownloadUrl());
                }else {
                    Log.d("Main","Response code is "+response.code());
                    Toast.makeText(PatientRegistrationActivity.this, "Error Uploading Profile Image, Please Try Again", Toast.LENGTH_SHORT).show();
                    try {
                        Log.d("Main",response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<ImageDto> call, Throwable t) {
                Log.d("Main",t.getMessage());
                Toast.makeText(PatientRegistrationActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        }catch (Exception exception){
            Log.d("Main","The error is "+exception.toString());
        }
    }
    private void getAllPatients(){
        Call<ResponseBody> call = BaseUrlClient
                .getInstance().getApi().getAllPatient();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code()==200){
                    try {
                        Log.d("Main","The response is "+response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    Log.d("Main", "The response code is "+response.code());
                    Log.d("Main","Error  message is "+response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Main","Server error is "+t.getMessage());
            }
        });
    }

    public static String getDriveFile(Context context, Uri uri) {
        Uri returnUri = uri;
        Cursor returnCursor = context.getContentResolver().query(returnUri, null, null, null, null);

        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
        returnCursor.moveToFirst();
        String name = (returnCursor.getString(nameIndex));
        String size = (Long.toString(returnCursor.getLong(sizeIndex)));
        File file = new File(context.getCacheDir(), name);
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            FileOutputStream outputStream = new FileOutputStream(file);
            int read = 0;
            int maxBufferSize = 1 * 1024 * 1024;
            int bytesAvailable = inputStream.available();

            //int bufferSize = 1024;
            int bufferSize = Math.min(bytesAvailable, maxBufferSize);

            final byte[] buffers = new byte[bufferSize];
            while ((read = inputStream.read(buffers)) != -1) {
                outputStream.write(buffers, 0, read);
            }
            Log.e("File Size", "Size " + file.length());
            inputStream.close();
            outputStream.close();
            Log.e("File Path", "Path " + file.getPath());
            Log.e("File Size", "Size " + file.length());
        } catch (Exception e) {
            Log.e("Exception", e.getMessage());
        }
        return file.getPath();

    }

}