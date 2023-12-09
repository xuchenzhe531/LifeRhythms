package com.cs407.LifeRhythms;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.util.Log;
import java.text.DateFormat;
import java.util.Locale;
import java.text.ParseException;

public class noteWriting extends AppCompatActivity {
    private EditText dateEditText;
    private EditText startTimeEditText;
    private EditText endTimeEditText;
    private EditText todoEditText;
    private Spinner categorySpinner;
    public int noteid = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_writing);

        Button saveButton = findViewById(R.id.saveButton);
        Button deleteButton = findViewById(R.id.deleteButton);
//*********************************************************
        dateEditText = findViewById(R.id.dateEditText);
        startTimeEditText = findViewById(R.id.startTimeEditText);
        endTimeEditText = findViewById(R.id.endTimeEditText);
        todoEditText = findViewById(R.id.todoEditText);
        categorySpinner = findViewById(R.id.categorySpinner);

        String[] categories = {"Study", "Work", "Break"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);
//*********************************************************
        Intent intent = getIntent();
        String str = intent.getStringExtra("noteid");
        if (str != null) {
            noteid = Integer.parseInt(str);
        }

        Log.i("lab5", "NoteID: " + str);

        if (noteid != -1) {
            Notes note = NotesActivity.note.get(noteid);
            String noteContent = note.getContent();
            //noteEditText.setText(noteContent);
            String desireDate;
            String StartTime;
            String endTime;
            String todo;
            String[] parts = noteContent.split("\\*");
            desireDate = parts[0];
            StartTime = parts[1];
            endTime = parts[2];
            todo = parts[3];

            dateEditText.setText(desireDate);
            startTimeEditText.setText(StartTime);
            endTimeEditText.setText(endTime);
            todoEditText.setText(todo);
        }

        saveButton.setOnClickListener(v -> saveMethod());
        deleteButton.setOnClickListener(v -> deleteMethod());
    }

    public void saveMethod() {
        //String content = noteEditText.getText().toString();
        String desireDate;
        String StartTime;
        String endTime;
        String todo;
        String category;
        desireDate = dateEditText.getText().toString();
        StartTime = startTimeEditText.getText().toString();
        endTime = endTimeEditText.getText().toString();
        todo = todoEditText.getText().toString();
        category = categorySpinner.getSelectedItem().toString();

        if (!isValidDateFormat(desireDate)) {
            Toast.makeText(this, "Invalid date format. Please use yyyy/MM/dd format.", Toast.LENGTH_SHORT).show();
            return; // 阻止继续执行
        }

        // 验证时间格式
        if (!isValidTimeFormat(StartTime) || !isValidTimeFormat(endTime)) {
            Toast.makeText(this, "Invalid time format. Please use HH:mm format.", Toast.LENGTH_SHORT).show();
            return; // 阻止继续执行
        }

        String content = desireDate+"*"+StartTime+"*"+endTime+"*"+todo+"*"+category;


        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);

        SQLiteDatabase sqLiteDatabaseC = context.openOrCreateDatabase("calendars", Context.MODE_PRIVATE, null);
        CalendarDBHelper CalendardbHelper = new CalendarDBHelper(sqLiteDatabaseC);

        SharedPreferences sharedPreferences = getSharedPreferences("com.cs407.LifeRhythms", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");

        String title;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.US);
        String date = dateFormat.format(new Date());

        if (noteid == -1) {
            title = "CALENDAR_" + (NotesActivity.note.size() + 1);
            dbHelper.saveNotes(username, title, content, date);
            CalendardbHelper.saveCalendars(username,date,title,desireDate,StartTime,endTime,todo,category);
        } else {
            title = "CALENDAR_" + (noteid + 1);
            dbHelper.updateNote(title, date, content, username);
            CalendardbHelper.updateCalendar(username,date,title,desireDate,StartTime,endTime,todo,category);
        }

        Intent intent = new Intent(this, NotesActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean isValidDateFormat(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd", Locale.US);
        format.setLenient(false);
        try {
            Date parsedDate = format.parse(date);
            return format.format(parsedDate).equals(date);
        } catch (ParseException e) {
            return false;
        }
    }

    private boolean isValidTimeFormat(String time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.US);
        try {
            Date parsedTime = format.parse(time);
            return format.format(parsedTime).equals(time);
        } catch (ParseException e) {
            return false;
        }
    }


    public void deleteMethod() {
        if (noteid != -1) {
            Context context = getApplicationContext();
            SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);
            DBHelper dbHelper = new DBHelper(sqLiteDatabase);

            SQLiteDatabase sqLiteDatabaseC = context.openOrCreateDatabase("calendars", Context.MODE_PRIVATE, null);
            CalendarDBHelper CalendardbHelper = new CalendarDBHelper(sqLiteDatabaseC);

            String title = "CALENDAR_" + (noteid + 1);
            //String content = noteEditText.getText().toString();
            String desireDate;
            String StartTime;
            String endTime;
            String todo;
            String category;
            desireDate = dateEditText.getText().toString();
            StartTime = startTimeEditText.getText().toString();
            endTime = endTimeEditText.getText().toString();
            todo = todoEditText.getText().toString();
            category = categorySpinner.getSelectedItem().toString();
            String content = desireDate+"*"+StartTime+"*"+endTime+"*"+todo+"*"+category;

            dbHelper.deleteNotes(content, title);
            CalendardbHelper.deleteCalendars(desireDate,StartTime,endTime,todo,category,title);

            Intent intent = new Intent(this, NotesActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}

