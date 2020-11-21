package com.baloonink.crashthebones;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class MainActivity extends AppCompatActivity {

    public static final String TIME_KEY = "TIMER";
    public static final String KNIGHT_KEY = "KNIGHT";
    private TextView textView;
    private Button start_btn;
    private Button exit_btn;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_menu);
        textView = findViewById(R.id.welcome);
        start_btn = findViewById(R.id.start_btn);
        exit_btn = findViewById(R.id.exit_btn);
        start_btn.setOnClickListener(v -> {
            setContentView(new GameView(this));
        });
        exit_btn.setOnClickListener(v -> {
            finish();
        });
    }

    public void startEndActivity(double game_time, boolean is_hero_killed) {
        intent = new Intent(this, EndActivity.class);
        intent.putExtra(TIME_KEY, game_time);
        intent.putExtra(KNIGHT_KEY, is_hero_killed);
        startActivity(intent);
        finish();
    }

}