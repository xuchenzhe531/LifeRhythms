package com.cs407.lab5_milestone;

import static com.cs407.lab5_milestone.DBHelper.sqLiteDatabase;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class noteWriting extends AppCompatActivity {
    private EditText noteEditText;
    private Button saveButton;
    private Button deleteButton;
    public int noteid = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_writing);  // 设置对应的布局文件

        noteEditText = findViewById(R.id.noteEditText);
        saveButton = findViewById(R.id.saveButton);
        deleteButton = findViewById(R.id.deleteButton);

        Intent intent = getIntent();
        String str = intent.getStringExtra("noteid");
        if (str != null) {
            noteid = Integer.parseInt(str);
        }

        Log.i("lab5", "NoteID: " + str);

        if (noteid != -1) {
            Notes note = NotesActivity.note.get(noteid);
            String noteContent = note.getContent();
            noteEditText.setText(noteContent);
        }


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMethod();
            }
        });

        Button deleteButton = findViewById(R.id.deleteButton);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteMethod();
            }
        });
    }

    public void saveMethod() {
        String content = noteEditText.getText().toString();

        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);

        SharedPreferences sharedPreferences = getSharedPreferences("com.cs407.lab5_milestone", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");

        String title;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date = dateFormat.format(new Date());

        if (noteid == -1) {
            title = "NOTES_" + (NotesActivity.note.size() + 1);
            dbHelper.saveNotes(username, title, content, date);
        } else {
            title = "NOTES_" + (noteid + 1);
            dbHelper.updateNote(title, date, content, username);
        }

        Intent intent = new Intent(this, NotesActivity.class);
        startActivity(intent);
        finish();
    }

    public void deleteMethod() {
        if (noteid != -1) {
            Context context = getApplicationContext();
            SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);
            DBHelper dbHelper = new DBHelper(sqLiteDatabase);

            String title = "NOTES_" + (noteid + 1);
            String content = noteEditText.getText().toString();

            dbHelper.deleteNotes(content, title);

            Intent intent = new Intent(this, NotesActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}
