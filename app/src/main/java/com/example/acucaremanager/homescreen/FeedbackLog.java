package com.example.acucaremanager.homescreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.acucaremanager.R;
import com.example.acucaremanager.loginandregister.SharedPreferenceConfig;
import com.example.acucaremanager.loginandregister.UserLogin;
import com.example.acucaremanager.feedback.AddFeedbackActivity;
import com.example.acucaremanager.feedback.ViewFeedbackActivity;
import com.example.acucaremanager.feedback.SearchFeedbackActivity;
import com.example.acucaremanager.feedback.EditFeedbackActivity;
import com.example.acucaremanager.feedback.DeleteFeedbackActivity;
import com.google.firebase.auth.FirebaseAuth;

public class FeedbackLog extends AppCompatActivity {

    AppCompatButton addFeedback, viewFeedback, searchFeedback, editFeedback, deleteFeedback, logoutFeedback;
    FirebaseAuth mAuth;
    private SharedPreferenceConfig sharedPreferenceConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_log);

        mAuth = FirebaseAuth.getInstance();
        sharedPreferenceConfig = new SharedPreferenceConfig(getApplicationContext());

        // Bind Buttons
        addFeedback = findViewById(R.id.addFeedback);
        viewFeedback = findViewById(R.id.viewFeedback);
        searchFeedback = findViewById(R.id.searchFeedback);
        editFeedback = findViewById(R.id.editFeedback);
        deleteFeedback = findViewById(R.id.deleteFeedback);
        logoutFeedback = findViewById(R.id.logoutFeedback);

        // ✅ Navigate to Add Feedback
        addFeedback.setOnClickListener(v ->
                startActivity(new Intent(FeedbackLog.this, AddFeedbackActivity.class)));

        // ✅ Navigate to View Feedback
        viewFeedback.setOnClickListener(v ->
                startActivity(new Intent(FeedbackLog.this, ViewFeedbackActivity.class)));

        // ✅ Navigate to Search Feedback
        searchFeedback.setOnClickListener(v ->
                startActivity(new Intent(FeedbackLog.this, SearchFeedbackActivity.class)));

        // ✅ Navigate to Edit Feedback
        editFeedback.setOnClickListener(v ->
                startActivity(new Intent(FeedbackLog.this, EditFeedbackActivity.class)));

        // ✅ Navigate to Delete Feedback
        deleteFeedback.setOnClickListener(v ->
                startActivity(new Intent(FeedbackLog.this, DeleteFeedbackActivity.class)));

        // ✅ Logout Logic
        logoutFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                sharedPreferenceConfig.writeLoginStatus(false);
                Intent intent = new Intent(FeedbackLog.this, UserLogin.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
