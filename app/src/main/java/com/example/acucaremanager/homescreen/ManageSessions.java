package com.example.acucaremanager.homescreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.acucaremanager.R;
import com.example.acucaremanager.loginandregister.SharedPreferenceConfig;
import com.example.acucaremanager.loginandregister.UserLogin;
import com.example.acucaremanager.managesession.AddSessionActivity;
import com.example.acucaremanager.managesession.DeleteSessionActivity;
import com.example.acucaremanager.managesession.EditSessionActivity;
import com.example.acucaremanager.managesession.SearchSessionsActivity;
import com.example.acucaremanager.managesession.SearchSessionsActivity;
import com.example.acucaremanager.managesession.ViewAllSessionsActivity;
import com.example.acucaremanager.managesession.ViewAllSessionsActivity;
import com.google.firebase.auth.FirebaseAuth;

public class ManageSessions extends AppCompatActivity {

    AppCompatButton addSession, deleteSession, editSession, searchSession, viewSession, logout;
    FirebaseAuth mAuth;
    private SharedPreferenceConfig sharedPreferenceConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_sessions);

        mAuth = FirebaseAuth.getInstance();
        sharedPreferenceConfig = new SharedPreferenceConfig(getApplicationContext());

        logout = findViewById(R.id.b1);
        addSession = findViewById(R.id.addSession);
        deleteSession = findViewById(R.id.deleteSession);
        editSession = findViewById(R.id.editSession);
        searchSession = findViewById(R.id.searchSession);
        viewSession = findViewById(R.id.viewAllSession);

        // ✅ Logout Logic
        logout.setOnClickListener(v -> {
            mAuth.signOut();
            sharedPreferenceConfig.writeLoginStatus(false);
            Intent i = new Intent(ManageSessions.this, UserLogin.class);
            startActivity(i);
            finish();
        });

        // ✅ Open Add Session Screen
        addSession.setOnClickListener(v -> startActivity(new Intent(ManageSessions.this, AddSessionActivity.class)));

        // ✅ Open Delete Session Screen
        deleteSession.setOnClickListener(v -> startActivity(new Intent(ManageSessions.this, DeleteSessionActivity.class)));

        // ✅ Open Edit Session Screen
        editSession.setOnClickListener(v -> startActivity(new Intent(ManageSessions.this, EditSessionActivity.class)));

        // ✅ Open Search Session Screen
        searchSession.setOnClickListener(v -> startActivity(new Intent(ManageSessions.this, SearchSessionsActivity.class)));

        // ✅ Open View All Sessions Screen
        viewSession.setOnClickListener(v -> startActivity(new Intent(ManageSessions.this, ViewAllSessionsActivity.class)));
    }
}
