package com.example.acucaremanager.homescreen;

<<<<<<< HEAD
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
import com.example.acucaremanager.managesession.ViewAllSessionsActivity;
import com.google.firebase.auth.FirebaseAuth;

public class ManageSessions extends AppCompatActivity {
    AppCompatButton addSession,deleteSession,editSession,searchSession,viewSession,logout;
    FirebaseAuth mAuth;
    private SharedPreferenceConfig sharedPreferenceConfig;
=======
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.acucaremanager.R;

public class ManageSessions extends AppCompatActivity {

>>>>>>> 3924d79200926a70af7da7db7a9f2b79a926b384
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_sessions);
<<<<<<< HEAD
        logout = findViewById(R.id.b1);
        addSession = findViewById(R.id.addSession);
        deleteSession = findViewById(R.id.deleteSession);
        editSession = findViewById(R.id.editSession);
        searchSession = findViewById(R.id.searchSession);
        viewSession = findViewById(R.id.viewAllSession);
        
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent i = new Intent(ManageSessions.this, UserLogin.class);
                startActivity(i);
                finish();
                sharedPreferenceConfig.writeLoginStatus(false);
            }
        });

        addSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(ManageSessions.this, AddSessionActivity.class);
                startActivity(i);
            }
        });



        deleteSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ManageSessions.this, DeleteSessionActivity.class);
                startActivity(i);
            }
        });


        editSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ManageSessions.this, EditSessionActivity.class);
                startActivity(i);
            }
        });
        searchSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ManageSessions.this, SearchSessionsActivity.class);
                startActivity(i);
            }
        });
        viewSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ManageSessions.this, ViewAllSessionsActivity.class);
                startActivity(i);
            }
        });
        
=======
>>>>>>> 3924d79200926a70af7da7db7a9f2b79a926b384
    }
}