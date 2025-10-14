package com.example.acucaremanager.homescreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.acucaremanager.R;
import com.example.acucaremanager.loginandregister.SharedPreferenceConfig;
import com.example.acucaremanager.loginandregister.UserLogin;
import com.google.firebase.auth.FirebaseAuth;

public class UserHomeScreen extends AppCompatActivity {
    androidx.appcompat.widget.AppCompatButton logout, manageClient, manageSession, feedbackLog, financeSummary;
    FirebaseAuth mAuth;
    private SharedPreferenceConfig sharedPreferenceConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_screen);
        mAuth = FirebaseAuth.getInstance();
        sharedPreferenceConfig = new SharedPreferenceConfig(getApplicationContext());
        logout = findViewById(R.id.b1);
        manageClient = findViewById(R.id.manageClient);
        manageSession = findViewById(R.id.manageSessions);
        feedbackLog = findViewById(R.id.feedbackLog);
        financeSummary = findViewById(R.id.financeSummary);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent i = new Intent(UserHomeScreen.this, UserLogin.class);
                startActivity(i);
                finish();
                sharedPreferenceConfig.writeLoginStatus(false);
            }
        });

        manageClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserHomeScreen.this, ManageClients.class);
                startActivity(i);
            }
        });
        feedbackLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserHomeScreen.this, FeedbackLog.class);
                startActivity(i);
            }
        });
        financeSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserHomeScreen.this, FinanceSummary.class);
                startActivity(i);
            }
        });
        manageSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserHomeScreen.this, ManageSessions   .class);
                startActivity(i);
            }
        });
    }
}