package com.example.acucaremanager.manageclient;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.acucaremanager.R;
import com.example.acucaremanager.pojo.ClientPojo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EditClientActivity extends AppCompatActivity {

    Spinner spinEditClient;
    EditText etEditClientName, etEditClientContact, etEditClientNotes;
    AppCompatButton btnUpdateClient;
    DatabaseReference dr;
    ArrayList<String> clientNames = new ArrayList<>();
    ArrayAdapter<String> adapter;

    String selectedOldName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_client);

        dr = FirebaseDatabase.getInstance("https://acucaremanager111-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Clients");

        spinEditClient = findViewById(R.id.spinEditClient);
        etEditClientName = findViewById(R.id.etEditClientName);
        etEditClientContact = findViewById(R.id.etEditClientContact);
        etEditClientNotes = findViewById(R.id.etEditClientNotes);
        btnUpdateClient = findViewById(R.id.btnUpdateClient);

        // Setup spinner adapter
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, clientNames);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinEditClient.setAdapter(adapter);

        // Load all client names into spinner
        loadClientNames();

        // On client selected → load data
        spinEditClient.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> adapterView, View view, int i, long l) {
                selectedOldName = clientNames.get(i);
                if (!selectedOldName.equals("Select client")) {
                    loadClientDetails(selectedOldName);
                }
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> adapterView) {}
        });

        // ✅ Update Client Logic
        btnUpdateClient.setOnClickListener(v -> {
            String newName = etEditClientName.getText().toString().trim();
            String newContact = etEditClientContact.getText().toString().trim();
            String newNotes = etEditClientNotes.getText().toString().trim();

            if (newName.isEmpty() || newContact.isEmpty()) {
                Toast.makeText(this, "Name & Contact required", Toast.LENGTH_SHORT).show();
                return;
            }

            // Delete old node
            dr.child(selectedOldName).removeValue();

            // Add new updated node
            ClientPojo updatedClient = new ClientPojo(newName, newContact, newNotes);
            dr.child(newName).setValue(updatedClient).addOnCompleteListener(task -> {
                Toast.makeText(this, "Client Updated Successfully", Toast.LENGTH_SHORT).show();
                finish();
            });
        });
    }

    private void loadClientNames() {
        dr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clientNames.clear();
                clientNames.add("Select client");
                for (DataSnapshot ds : snapshot.getChildren()) {
                    clientNames.add(ds.getKey());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void loadClientDetails(String clientName) {
        dr.child(clientName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ClientPojo client = snapshot.getValue(ClientPojo.class);
                if (client != null) {
                    etEditClientName.setText(client.getName());
                    etEditClientContact.setText(client.getContact());
                    etEditClientNotes.setText(client.getNotes());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}