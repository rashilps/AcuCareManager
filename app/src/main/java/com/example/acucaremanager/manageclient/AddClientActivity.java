package com.example.acucaremanager.manageclient;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.acucaremanager.R;
import com.example.acucaremanager.pojo.ClientPojo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AddClientActivity extends AppCompatActivity {

    private EditText etClientName, etClientContact, etClientNotes;
    private Button btnSaveClient;
    private DatabaseReference dr;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);
        dr = FirebaseDatabase.getInstance("https://acucaremanager111-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Clients");

        etClientName = findViewById(R.id.etClientName);
        etClientContact = findViewById(R.id.etClientContact);
        etClientNotes = findViewById(R.id.etClientNotes);
        btnSaveClient = findViewById(R.id.btnSaveClient);

        // Save client on button click
        btnSaveClient.setOnClickListener(v -> {
//            Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();

            String name = etClientName.getText().toString().trim();
            String contact = etClientContact.getText().toString().trim();
            String notes = etClientNotes.getText().toString().trim();
            ClientPojo cli =new ClientPojo(name,contact,notes);
            if (name.isEmpty() || contact.isEmpty()) {
                Toast.makeText(AddClientActivity.this, "Name and Contact are required", Toast.LENGTH_SHORT).show();

            }
            else {
                dr.child(name).setValue(cli).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        etClientName.setText("");
                        etClientContact.setText("");
                        etClientNotes.setText("");

                        Toast.makeText(AddClientActivity.this, "New client added", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });
    }
}