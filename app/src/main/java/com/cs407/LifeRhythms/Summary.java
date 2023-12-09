package com.cs407.LifeRhythms;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.database.sqlite.SQLiteDatabase;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Summary extends AppCompatActivity {

    private TextView studyCountTextView;
    private TextView breakCountTextView;
    private TextView workCountTextView;
    private SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        studyCountTextView = findViewById(R.id.studyCountTextView);
        breakCountTextView = findViewById(R.id.breakCountTextView);
        workCountTextView = findViewById(R.id.workCountTextView);

        updateCounts();

        Button summaryButton = findViewById(R.id.summaryButton);
        summaryButton.setOnClickListener(v -> {
            Intent intent = new Intent(Summary.this, ScheduleListActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCounts();
    }

    private void updateCounts() {
        SharedPreferences sharedPreferences = getSharedPreferences("com.cs407.LifeRhythms", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");

        if (username.isEmpty()) {
            studyCountTextView.setText("No user logged in");
            return;
        }

        sqLiteDatabase = openOrCreateDatabase("calendars", MODE_PRIVATE, null);
        CalendarDBHelper calendarDBHelper = new CalendarDBHelper(sqLiteDatabase);

        ArrayList<ToDo> toDoList = calendarDBHelper.readCalendars(username);

        Map<String, Integer> counts = new HashMap<>();
        counts.put("Study", 0);
        counts.put("Break", 0);
        counts.put("Work", 0);

        for (ToDo toDo : toDoList) {
            String category = toDo.getCategory();
            counts.put(category, counts.getOrDefault(category, 0) + 1);
        }

        studyCountTextView.setText("Study Count: " + counts.getOrDefault("Study", 0));
        breakCountTextView.setText("Break Count: " + counts.getOrDefault("Break", 0));
        workCountTextView.setText("Work Count: " + counts.getOrDefault("Work", 0));

        if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
            sqLiteDatabase.close();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
            sqLiteDatabase.close();
        }
    }
}
