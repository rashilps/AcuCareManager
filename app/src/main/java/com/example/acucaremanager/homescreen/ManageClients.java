package com.example.acucaremanager.homescreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.acucaremanager.R;
import com.example.acucaremanager.loginandregister.SharedPreferenceConfig;
import com.example.acucaremanager.loginandregister.UserLogin;
import com.example.acucaremanager.manageclient.AddClientActivity;
import com.example.acucaremanager.manageclient.DeleteClientActivity;
import com.example.acucaremanager.manageclient.EditClientActivity;
import com.example.acucaremanager.manageclient.SearchClientActivity;
import com.example.acucaremanager.manageclient.ViewClientsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.example.acucaremanager.R;
import com.example.acucaremanager.loginandregister.SharedPreferenceConfig;
import com.example.acucaremanager.loginandregister.UserLogin;
import com.google.firebase.auth.FirebaseAuth;
public class ManageClients extends AppCompatActivity {
    AppCompatButton addClient,deleteClient,editClient,searchClient,viewClient,logout;
    FirebaseAuth mAuth;
    private SharedPreferenceConfig sharedPreferenceConfig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_clients);
        addClient = findViewById(R.id.addClient);
        deleteClient = findViewById(R.id.deleteClient);
        editClient = findViewById(R.id.editClient);
        searchClient = findViewById(R.id.searchClient);
        viewClient = findViewById(R.id.viewAllClients);
        logout = findViewById(R.id.b1);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent i = new Intent(ManageClients.this, UserLogin.class);
                startActivity(i);
                finish();
                sharedPreferenceConfig.writeLoginStatus(false);
            }
        });
        addClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ManageClients.this, AddClientActivity.class);
                startActivity(i);
            }
        });



        deleteClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ManageClients.this, DeleteClientActivity.class);
                startActivity(i);
            }
        });


        editClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ManageClients.this, EditClientActivity.class);
                startActivity(i);
            }
        });
        searchClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ManageClients.this, SearchClientActivity.class);
                startActivity(i);
            }
        });
        viewClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ManageClients.this, ViewClientsActivity.class);
                startActivity(i);
            }
        });
    }
}