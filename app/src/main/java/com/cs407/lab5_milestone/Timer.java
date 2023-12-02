package com.cs407.lab5_milestone;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import androidx.appcompat.app.AppCompatActivity;

public class Timer extends AppCompatActivity {

    private TimePicker timePicker;
    private Button startButton;
    private MediaPlayer mediaPlayer;
    private TextView timeRemainingTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        timePicker = findViewById(R.id.timePicker);
        startButton = findViewById(R.id.startButton);
        timeRemainingTextView = findViewById(R.id.timeRemainingTextView);

        timePicker.setIs24HourView(true);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour = timePicker.getHour();
                int minute = timePicker.getMinute();
                int seconds = (hour * 3600 + minute * 60) * 1000;

                new CountDownTimer(seconds, 1000) {

                    public void onTick(long millisUntilFinished) {
                        // 这里可以更新UI，显示剩余时间
                        int hours = (int) (millisUntilFinished / 3600000);
                        int minutes = (int) (millisUntilFinished % 3600000) / 60000;
                        int secs = (int) (millisUntilFinished % 60000) / 1000;
                        String time = String.format("%02d:%02d:%02d", hours, minutes, secs);
                        timeRemainingTextView.setText(time);
                    }

                    public void onFinish() {
                        // 倒计时结束，播放声音
                        playSound();
                        timeRemainingTextView.setText("00:00:00");
                    }
                }.start();
            }
        });
        // 在 onCreate 方法中

// 初始化返回按钮并设置点击事件
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建一个意图以启动 MainActivity
                Intent intent = new Intent(Timer.this, WelcomePage.class);
                startActivity(intent);
                // 结束当前活动
                finish();
            }
        });

    }

    private void playSound() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(this, R.raw.warning);
        mediaPlayer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }


}