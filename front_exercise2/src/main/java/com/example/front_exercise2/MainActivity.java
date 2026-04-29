package com.example.front_exercise2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(new GameView(this));
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnStart){
            Intent intent = new Intent(this, GameActivity.class);
            startActivity(intent);
        }
        if (v.getId() == R.id.btnExit){
            finish();
        }
    }
}