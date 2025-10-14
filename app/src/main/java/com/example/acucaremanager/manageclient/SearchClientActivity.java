package com.example.acucaremanager.manageclient;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.acucaremanager.R;
import com.example.acucaremanager.adapters.ClientSearchAdapter;
import com.example.acucaremanager.pojo.ClientPojo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchClientActivity extends AppCompatActivity {

    private EditText etSearchClient;
    private RecyclerView recyclerView;
    private ClientSearchAdapter adapter;
    private ArrayList<ClientPojo> clientList = new ArrayList<>();

    private DatabaseReference dr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_client);

        // Firebase Reference
        dr = FirebaseDatabase.getInstance("https://acucaremanager111-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Clients");

        etSearchClient = findViewById(R.id.etSearchClient);
        recyclerView = findViewById(R.id.recyclerSearchClient);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ClientSearchAdapter(clientList);
        recyclerView.setAdapter(adapter);

        loadClients(); // Fetch data from Firebase

        // 🔍 Live Search Filter
        etSearchClient.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterClients(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void loadClients() {
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clientList.clear();
                for (DataSnapshot clientSnapshot : snapshot.getChildren()) {
                    ClientPojo client = clientSnapshot.getValue(ClientPojo.class);
                    clientList.add(client);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SearchClientActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filterClients(String text) {
        ArrayList<ClientPojo> filteredList = new ArrayList<>();
        for (ClientPojo client : clientList) {
            if (client.getName() != null && client.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(client);
            }
        }
        adapter.filterList(filteredList);
    }
}