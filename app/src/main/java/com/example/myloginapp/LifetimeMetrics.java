package com.example.myloginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class LifetimeMetrics extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    TextView title;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifetime_metrics);

        button = findViewById(R.id.LastRun_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LifetimeMetrics.this, MostRecentRun.class);
                startActivity(intent);
            }
        });

        title = findViewById(R.id.title);
        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.RunningMetrics_dash);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.RunningMetrics_dash:
                    return true;
                case R.id.Home_dash:
                    startActivity(new Intent(getApplicationContext(), MainMenu.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.Program_dash:
                    startActivity(new Intent(getApplicationContext(), MainProgramModule.class));
                    overridePendingTransition(0, 0);
                    return true;
            }

            return true;
        });
    }
}