package com.cs407.lab5_milestone;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Define views
    private EditText usernameEditText;
    private Button loginButton;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences("com.cs407.lab5_milestone", Context.MODE_PRIVATE);

        // Check if the username exists in the shared preferences
        String storedUsername = sharedPreferences.getString("username", "");
        if (!storedUsername.equals("")) {
            // Start the second screen (NotesActivity)
            Intent intent = new Intent(MainActivity.this, NotesActivity.class);
            intent.putExtra("username", storedUsername);
            startActivity(intent);
            finish();  // close the current activity
        } else {
            // Start the login screen activity
            setContentView(R.layout.activity_main);
            usernameEditText = findViewById(R.id.username);
            loginButton = findViewById(R.id.login_button);
            loginButton.setOnClickListener(this);
        }
    }

    // Handle button clicks here
    @Override
    public void onClick(View view) {
        if (view == loginButton) {
            String username = usernameEditText.getText().toString();
            sharedPreferences.edit().putString("username", username).apply();

            Intent intent = new Intent(MainActivity.this, NotesActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
            finish();  // close the login activity
        }
        // Handle other button clicks if needed
    }
}

