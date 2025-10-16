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

public class ManageClients extends AppCompatActivity {

    AppCompatButton addClient, deleteClient, editClient, searchClient, viewClient, logout;
    FirebaseAuth mAuth;
    private SharedPreferenceConfig sharedPreferenceConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_clients);

        mAuth = FirebaseAuth.getInstance();
        sharedPreferenceConfig = new SharedPreferenceConfig(getApplicationContext());

        addClient = findViewById(R.id.addClient);
        deleteClient = findViewById(R.id.deleteClient);
        editClient = findViewById(R.id.editClient);
        searchClient = findViewById(R.id.searchClient);
        viewClient = findViewById(R.id.viewAllClients);
        logout = findViewById(R.id.b1);

        // ✅ Logout Logic
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                sharedPreferenceConfig.writeLoginStatus(false);
                Intent i = new Intent(ManageClients.this, UserLogin.class);
                startActivity(i);
                finish();
            }
        });

        // ✅ Open Add Client Screen
        addClient.setOnClickListener(v -> startActivity(new Intent(ManageClients.this, AddClientActivity.class)));

        // ✅ Open Delete Client Screen
        deleteClient.setOnClickListener(v -> startActivity(new Intent(ManageClients.this, DeleteClientActivity.class)));

        // ✅ Open Edit Client Screen
        editClient.setOnClickListener(v -> startActivity(new Intent(ManageClients.this, EditClientActivity.class)));

        // ✅ Open Search Client Screen
        searchClient.setOnClickListener(v -> startActivity(new Intent(ManageClients.this, SearchClientActivity.class)));

        // ✅ Open View All Clients Screen
        viewClient.setOnClickListener(v -> startActivity(new Intent(ManageClients.this, ViewClientsActivity.class)));
    }
}
