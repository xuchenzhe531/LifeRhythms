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


public class noteWriting extends AppCompatActivity {

    private EditText noteEditText;
    private Button saveButton;
    private SharedPreferences sharedPreferences;
    private Button deleteButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_writing);


        noteEditText = findViewById(R.id.editTextNote);
        saveButton = findViewById(R.id.buttonSave);

        sharedPreferences = getSharedPreferences("com.cs407.lab5_milestone", Context.MODE_PRIVATE);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });

        if (getIntent().hasExtra("selectedNote")) {
            String note = getIntent().getStringExtra("selectedNote");
            noteEditText.setText(note);
        }

        deleteButton = findViewById(R.id.buttonDelete);  // 请确保 ID 与你的布局文件中的 ID 匹配
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNote();
            }
        });


    }

    private void saveNote() {
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);
        String note = noteEditText.getText().toString();

        if (note.isEmpty()) {
            Toast.makeText(this, "Please write a note before saving", Toast.LENGTH_SHORT).show();
            return;
        }

        Set<String> notesSet = new HashSet<>(sharedPreferences.getStringSet("notes", new HashSet<String>()));

        // 如果有传递过来的原有笔记，先从 notesSet 中删除它
        if (getIntent().hasExtra("selectedNote")) {
            String originalNote = getIntent().getStringExtra("selectedNote");
            notesSet.remove(originalNote);
        }

        notesSet.add(note);
        sharedPreferences.edit().putStringSet("notes", notesSet).apply();
        finish();
    }

    private void deleteNote() {
        if (getIntent().hasExtra("selectedNote")) {
            Context context = getApplicationContext();
            SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);
            DBHelper dbHelper = new DBHelper(sqLiteDatabase);
            String noteToDelete = getIntent().getStringExtra("selectedNote");

            Set<String> notesSet = new HashSet<>(sharedPreferences.getStringSet("notes", new HashSet<String>()));

            if (notesSet.remove(noteToDelete)) {  // 如果删除成功
                sharedPreferences.edit().putStringSet("notes", notesSet).apply();
                Toast.makeText(this, "Note deleted successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Error deleting note", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "No note to delete", Toast.LENGTH_SHORT).show();
        }
    }


}

