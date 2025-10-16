package com.example.acucaremanager.managesession;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.acucaremanager.R;
import com.example.acucaremanager.pojo.ClientPojo;
import com.example.acucaremanager.pojo.SessionPojo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class AddSessionActivity extends AppCompatActivity {

    Spinner spinnerClient;
    AppCompatButton btnPickDate, btnPickTime, btnSaveSession;
    TextView tvSelectedDate, tvSelectedTime;
    EditText etSessionFee, etSessionNotes;
    DatabaseReference drClients, drSessions;

    ArrayList<String> clientNames = new ArrayList<>();
    String selectedDate = "", selectedTime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_session);

        // Firebase References
        drClients = FirebaseDatabase.getInstance("https://acucaremanager111-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Clients");
        drSessions = FirebaseDatabase.getInstance("https://acucaremanager111-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Sessions"); // Will use push() here

        spinnerClient = findViewById(R.id.spinnerClient);
        btnPickDate = findViewById(R.id.btnPickDate);
        btnPickTime = findViewById(R.id.btnPickTime);
        btnSaveSession = findViewById(R.id.btnSaveSession);
        tvSelectedDate = findViewById(R.id.tvSelectedDate);
        tvSelectedTime = findViewById(R.id.tvSelectedTime);
        etSessionFee = findViewById(R.id.etSessionFee);
        etSessionNotes = findViewById(R.id.etSessionNotes);

        loadClientNames();
        setupDatePicker();
        setupTimePicker();

        btnSaveSession.setOnClickListener(v -> saveSession());
    }

    private void loadClientNames() {
        clientNames.clear();
        clientNames.add("Select Client"); // Default option

        drClients.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    ClientPojo cp = ds.getValue(ClientPojo.class);
                    if (cp != null) clientNames.add(cp.getName());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(AddSessionActivity.this, android.R.layout.simple_spinner_item, clientNames);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerClient.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {}
        });
    }

    private void setupDatePicker() {
        btnPickDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(AddSessionActivity.this,
                    (view, y, m, d) -> {
                        selectedDate = d + "-" + (m + 1) + "-" + y;
                        tvSelectedDate.setText("Date: " + selectedDate);
                    }, year, month, day);
            datePickerDialog.show();
        });
    }

    private void setupTimePicker() {
        btnPickTime.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(AddSessionActivity.this,
                    (view, h, m) -> {
                        selectedTime = String.format("%02d:%02d", h, m);
                        tvSelectedTime.setText("Time: " + selectedTime);
                    }, hour, minute, true); // true → 24hr, change to false for AM/PM
            timePickerDialog.show();
        });
    }

    private void saveSession() {
        String clientName = spinnerClient.getSelectedItem().toString();
        String fee = etSessionFee.getText().toString();
        String notes = etSessionNotes.getText().toString();

        if (clientName.equals("Select Client") || selectedDate.isEmpty() || selectedTime.isEmpty() || fee.isEmpty()) {
            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String sessionId = drSessions.push().getKey(); // ✅ Unique ID
        SessionPojo session = new SessionPojo(sessionId, clientName, selectedDate, selectedTime, fee, notes);

        drSessions.child(sessionId).setValue(session).addOnSuccessListener(unused -> {
            Toast.makeText(this, "Session Saved", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}