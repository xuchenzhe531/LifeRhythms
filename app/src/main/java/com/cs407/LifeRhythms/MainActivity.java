package com.cs407.LifeRhythms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences("com.cs407.LifeRhythms", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");

        if (!username.isEmpty()) {
            Intent intent = new Intent(MainActivity.this, NotesActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        setContentView(R.layout.activity_main);

        EditText usernameEditText = findViewById(R.id.username);
        Button loginButton = findViewById(R.id.login);

        loginButton.setOnClickListener(v -> {
            String enteredUsername = usernameEditText.getText().toString().trim();

            if (!enteredUsername.isEmpty()) {
                sharedPreferences.edit().putString("username", enteredUsername).apply();

                Intent intent = new Intent(MainActivity.this, WelcomePage.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(MainActivity.this, "Please enter a username", Toast.LENGTH_SHORT).show(); // 更清晰的提示信息
            }
        });
    }
}