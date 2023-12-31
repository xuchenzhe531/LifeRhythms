package com.cs407.LifeRhythms;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
import java.util.Locale;

public class ScheduleListActivity extends AppCompatActivity {

    private DatePicker datePicker;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_list);

        datePicker = findViewById(R.id.datePicker);
        listView = findViewById(R.id.lvSchedule);
        Button btnShowSchedule = findViewById(R.id.btnShowSchedule);
        Button btnBackToMain = findViewById(R.id.btnBackToMain);
        Button btnSummary = findViewById(R.id.btnSummary);

        btnShowSchedule.setOnClickListener(v -> {
            int day = datePicker.getDayOfMonth();
            int month = datePicker.getMonth() + 1;
            int year = datePicker.getYear();

            String selectedDate = String.format(Locale.US, "%d/%02d/%02d", year, month, day);
            loadScheduleForDate(selectedDate);
        });
        btnBackToMain.setOnClickListener(view -> {
            Intent intent = new Intent(this, WelcomePage.class);
            startActivity(intent);
        });
        btnSummary.setOnClickListener(view -> {
            Intent intent = new Intent(this, Summary.class);
            startActivity(intent);
        });
    }

    private void loadScheduleForDate(String date) {
        // 从数据库中检索日期为 `date` 的所有日程
        // 假设您有一个方法 getScheduleForDate(date) 来获取数据
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabaseC = context.openOrCreateDatabase("calendars", Context.MODE_PRIVATE, null);
        CalendarDBHelper CalendardbHelper = new CalendarDBHelper(sqLiteDatabaseC);

        SharedPreferences sharedPreferences = getSharedPreferences("com.cs407.LifeRhythms", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        List<ScheduleItem> scheduleItems = CalendardbHelper.getScheduleForDate(date, username);
        Log.i("Checker", "ListLength: " + scheduleItems.size());

        // 使用适配器显示日程列表
        ScheduleAdapter adapter = new ScheduleAdapter(this, scheduleItems);
        listView.setAdapter(adapter);
    }
}
