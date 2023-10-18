package com.cs407.lab5_milestone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class NotesActivity extends AppCompatActivity {

    private TextView welcomeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        welcomeTextView = findViewById(R.id.welcomeTextView);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        String welcomeMessage = "Welcome " + username + " to Notes App!";
        welcomeTextView.setText(welcomeMessage);
    }
}
