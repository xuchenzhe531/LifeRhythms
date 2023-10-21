package com.cs407.lab5_milestone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences("com.cs407.lab5_milestone", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");

        if (!username.equals("")) { // 如果username存在，则直接打开NotesActivity
            Intent intent = new Intent(MainActivity.this, NotesActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        setContentView(R.layout.activity_main);

        usernameEditText = findViewById(R.id.username);
        loginButton = findViewById(R.id.login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredUsername = usernameEditText.getText().toString().trim();

                if (!enteredUsername.equals("")) {
                    sharedPreferences.edit().putString("username", enteredUsername).apply();

                    Intent intent = new Intent(MainActivity.this, NotesActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // 提示用户输入用户名
                    Toast.makeText(MainActivity.this, "Username", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}


