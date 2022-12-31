package com.elijah.ukeme.doctorsappointmentapplication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.elijah.ukeme.doctorsappointmentapplication.R;
import com.elijah.ukeme.doctorsappointmentapplication.model.PatientDto;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import storage.SharedPreferenceManager;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    NavigationView navigationView;
    FrameLayout frameLayout;
    DrawerLayout drawerLayout;
    private CircleImageView imageView;
    Toolbar toolbar;
    TextView userName,userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        toolbar = findViewById(R.id.toolBar);
        frameLayout = findViewById(R.id.fragment_container);
        drawerLayout = findViewById(R.id.drawer_layout);
        userEmail = findViewById(R.id.useremail);
        userName = findViewById(R.id.username);
        navigationView = findViewById(R.id.nav_view);
        imageView = findViewById(R.id.profileimage);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_navigation_drawer,
                R.string.close_navigation_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        PatientDto patientDto = SharedPreferenceManager.getInstance(HomeActivity.this)
                .getPatient();

        try {
            navigationView.setNavigationItemSelectedListener(this);
            View view = navigationView.getHeaderView(0);
            TextView userName = view.findViewById(R.id.username);
            TextView userEmail = view.findViewById(R.id.useremail);
            CircleImageView circleImageView = view.findViewById(R.id.profileimage);
            userName.setText(patientDto.getName());
            userEmail.setText(patientDto.getEmail());
            Picasso.get().load(patientDto.getProfileImage()).into(circleImageView);
        }catch (Exception e){
            Log.d("Main","The error is "+e.getMessage());
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.book_appointment:
                gotoBookingActivity();
                break;
            case R.id.check_profile_info:
                gotoProfileActivity();
                break;
            case R.id.check_appointment:
                gotoAppointmentInformationActivity();
                break;
            case R.id.logout:
                logout();
                break;
            default:
                Toast.makeText(this, "Wrong selection", Toast.LENGTH_SHORT).show();

    }
    return true;
}
    private void gotoBookingActivity(){
        Intent intent = new Intent(getApplicationContext(),BookAppointmentActivity.class);
        startActivity(intent);
    }
    private void gotoProfileActivity(){
        Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
        startActivity(intent);
    }
    private void gotoAppointmentInformationActivity(){
        Intent intent = new Intent(getApplicationContext(),AppointmentInformationActivity.class);
        startActivity(intent);
    }
    private void logout(){
        SharedPreferenceManager.getInstance(HomeActivity.this).clear();
    }
}