package com.hybridev.dice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.StartDice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startDice = new Intent(MainActivity.this, DiceControl.class);
                startActivity(startDice);
            }
        });

    }
}