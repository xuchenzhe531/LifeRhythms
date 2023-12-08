package com.cs407.LifeRhythms;

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

public class NotesActivity extends AppCompatActivity {
    private TextView welcomeTextView;
    public static ArrayList<Notes> note = new ArrayList<>();
    public static ArrayList<ToDo> calendar = new ArrayList<>();
    private ArrayAdapter adapter;
    private ArrayList<String> displayNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        welcomeTextView = findViewById(R.id.welcomeText);

        SharedPreferences sharedPreferences = getSharedPreferences("com.cs407.LifeRhythms", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");

        if (!username.isEmpty()) {
            welcomeTextView.setText("Welcome, " + username + "!");
        }
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);
        note = dbHelper.readNotes(username);

        SQLiteDatabase sqLiteDatabaseC = context.openOrCreateDatabase("calendars", Context.MODE_PRIVATE, null);
        CalendarDBHelper CdbHelper = new CalendarDBHelper(sqLiteDatabaseC);
        calendar = CdbHelper.readCalendars(username);

        ArrayList<String> displayNotes = new ArrayList<>();
        for (ToDo ca : calendar){
            displayNotes.add(String.format("Title:%s\nAdded Date:%s\nCalendar Date:%s\nStart Time:%s\nEnd Time:%s\nToDo:%s\nCategory:%s\n", ca.getTitle(), ca.getDate(),ca.getDesiredDate(),ca.getStartTime(),ca.getEndTime(),ca.getTodo(),ca.getCategory()));
        }
//        ArrayList<String> displayNotes = new ArrayList<>();
//        for (Notes note : note) {
//            String content = note.getContent();
//            String[] parts = content.split("\\*");
//
//            displayNotes.add(String.format("Title:%s\nAdded Date:%s\nCalendar Date:%s\nStart Time:%s\nEnd Time:%s\nToDo:%s\nCategory:%s\n", note.getTitle(), note.getDate(),parts[0],parts[1],parts[2],parts[3],parts[4]));
//        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, displayNotes);

        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), noteWriting.class);
                intent.putExtra("noteid", String.valueOf(position));
                startActivity(intent);
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.add_note) {
            // 实现添加笔记的代码
            Intent intent = new Intent(this, noteWriting.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.logout) {
            SharedPreferences sharedPreferences = getSharedPreferences("com.cs407.LifeRhythms", MODE_PRIVATE);
            String username = sharedPreferences.getString("username", "");
            sharedPreferences.edit().clear().apply();
            Intent intent = new Intent(NotesActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }else if(id==R.id.backtowelcome){
            Intent intent = new Intent(this, WelcomePage.class);
            startActivity(intent);
            return true;
    }   else {
            return super.onOptionsItemSelected(item);
        }


    }



}



