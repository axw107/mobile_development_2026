package com.example.front_exercise2;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    GameField gameField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        gameField = findViewById(R.id.gameField);
        TextView tvScore = findViewById(R.id.tvScore);
        new CountDownTimer(Integer.MAX_VALUE, 200) {
            @Override
            public void onFinish() {}
            @Override
            public void onTick(long millisUntilFinished) {
                tvScore.setText(gameField.getScore() + "");
            }
        }.start();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnBack){
            finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameField.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameField.resume();
    }
}