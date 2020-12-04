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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    public static final String TIME_KEY = "TIMER";
    public static final String KNIGHT_KEY = "KNIGHT";
    private Intent intent;


    @BindView(R.id.welcome)
    TextView textView;

    @BindView(R.id.start_btn)
    Button start;

    @BindView(R.id.privacy)
    Button privacy;

    @BindView(R.id.exit_btn)
    Button exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_menu);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.start_btn)
    void onClickStart() {
        setContentView(new GameView(this));
    }

    @OnClick(R.id.privacy)
    void onClickPrivacy() {
        Intent intent = new Intent(MainActivity.this, PrivacyActivity.class);
        intent.putExtra("privacy", true);
        startActivity(intent);
    }

    @OnClick(R.id.exit_btn)
    void onClickExit() {
        finish();
    }


    public void startEndActivity(double game_time, boolean is_hero_killed) {
        intent = new Intent(this, EndActivity.class);
        intent.putExtra(TIME_KEY, game_time);
        intent.putExtra(KNIGHT_KEY, is_hero_killed);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
        System.exit(0);
    }
}