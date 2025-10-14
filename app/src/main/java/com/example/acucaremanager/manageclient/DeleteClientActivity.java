package com.example.acucaremanager.manageclient;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.acucaremanager.R;
import com.example.acucaremanager.pojo.ClientPojo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DeleteClientActivity extends AppCompatActivity implements View.OnClickListener {
    ArrayList<String> client;
    Spinner spinner;
    DatabaseReference dr;
    Button deleteClient;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_client);
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        dr = FirebaseDatabase.getInstance("https://acucaremanager111-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("/Clients");
        spinner = findViewById(R.id.spin);
////        spinner.setOnItemSelectedListener(this);
        deleteClient = findViewById(R.id.deleteClient);
        client = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, client);
        adapter.setDropDownViewResource(R.layout.spinner_item);


        dr.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                client.clear();
                client.add("Select item");
                for (DataSnapshot ds : snapshot.getChildren()) {
                    ClientPojo mp = ds.getValue(ClientPojo.class);
//                    System.out.println(mp);
                    String name = mp.getName();
//                    System.out.println(name);
                    client.add(name);
                    spinner.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
//
//
        deleteClient.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == deleteClient) {
            Toast.makeText(this, "Material deleted", Toast.LENGTH_SHORT).show();
            String itemSelected = spinner.getSelectedItem().toString();
//            System.out.println(dr.child("Material").child(itemSelected).getRef());
            FirebaseDatabase.getInstance("https://acucaremanager111-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Clients").child(itemSelected).removeValue();
            spinner.setAdapter(null);
            adapter.notifyDataSetChanged();

        }
    }
}