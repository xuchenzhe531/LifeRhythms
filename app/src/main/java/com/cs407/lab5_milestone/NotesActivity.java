package com.cs407.lab5_milestone;


import static com.cs407.lab5_milestone.DBHelper.sqLiteDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class NotesActivity extends AppCompatActivity {

    private TextView welcomeTextView;
    private ListView notesListView;
    private SharedPreferences sharedPreferences;
    private TextView usernameTextView;
    private DBHelper dbHelper;
    private String currentUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        welcomeTextView = findViewById(R.id.welcomeTextView);
        Context context = getApplicationContext();
        SQLiteDatabase sqlLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);
        DBHelper dbHelper = new DBHelper(sqlLiteDatabase);
        //Intent intent = getIntent();
        //String username = intent.getStringExtra("username");
        sharedPreferences = getSharedPreferences("com.cs407.lab5_milestone", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");

        String welcomeMessage = "Welcome " + username + " to Notes App!";
        welcomeTextView.setText(welcomeMessage);
        Toolbar toolbar = findViewById(R.id.notes_toolbar);
        setSupportActionBar(toolbar);

        notesListView = findViewById(R.id.notesListView);
        notesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedNote = (String) parent.getItemAtPosition(position);

                Intent intent = new Intent(NotesActivity.this, noteWriting.class);
                intent.putExtra("selectedNote", selectedNote);
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add_note) {
            // Start NoteWritingActivity here
            Intent intent = new Intent(this, noteWriting.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_logout) {
            sharedPreferences = getSharedPreferences("com.cs407.lab5_milestone", Context.MODE_PRIVATE);


            String username = sharedPreferences.getString("username", "");
            sharedPreferences.edit().clear().apply();
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNotes();
    }

    private void loadNotes() {
        Set<String> notesSet = sharedPreferences.getStringSet("notes", null);
        if (notesSet != null) {
            List<String> notesList = new ArrayList<>(notesSet);

            // 使用 ArrayAdapter 将 notesList 的内容显示在 notesListView 中
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notesList);
            notesListView.setAdapter(adapter);
        }
    }
}



