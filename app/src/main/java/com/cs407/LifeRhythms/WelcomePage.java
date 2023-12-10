package com.cs407.LifeRhythms;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

public class WelcomePage extends AppCompatActivity {

    private CheckBox checkBoxHealthy, checkBoxEnergized, checkBoxReady;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        checkBoxHealthy = findViewById(R.id.checkBoxHealthy);
        checkBoxEnergized = findViewById(R.id.checkBoxEnergized);
        checkBoxReady = findViewById(R.id.checkBoxReady);

        Button buttonGoToWork = findViewById(R.id.buttonGoToWork);
        Button buttonShowMyWork = findViewById(R.id.buttonShowMyWork);
        ImageButton buttonStart = findViewById(R.id.buttonStart);

        buttonGoToWork.setOnClickListener(v -> {
            if (allCheckBoxesChecked()) {
                Intent intent = new Intent(WelcomePage.this, NotesActivity.class);
                startActivity(intent);
            } else {
                showCheckboxWarning();
            }
        });

        buttonShowMyWork.setOnClickListener(v -> {
            if (allCheckBoxesChecked()) {
                Intent intent = new Intent(WelcomePage.this, ScheduleListActivity.class);
                startActivity(intent);
            } else {
                showCheckboxWarning();
            }
        });

        buttonStart.setOnClickListener(v -> {
            if (allCheckBoxesChecked()) {
                Intent intent = new Intent(WelcomePage.this, Timer.class);
                startActivity(intent);
            } else {
                showCheckboxWarning();
            }
        });
    }

    private boolean allCheckBoxesChecked() {
        return checkBoxHealthy.isChecked() && checkBoxEnergized.isChecked() && checkBoxReady.isChecked();
    }

    private void showCheckboxWarning() {
        Toast.makeText(WelcomePage.this, "You need to check all boxes to continue!", Toast.LENGTH_SHORT).show();
    }
}