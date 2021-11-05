package com.example.myloginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputLayout;

public class SelectWeek extends AppCompatActivity {

    TextInputLayout textInputLayout;
    AutoCompleteTextView autoCompleteTextView;

    private Button goto_week_btn;
    TextView program_details_textView;

    BottomNavigationView bottomNavigationView;
    TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_week);

        textInputLayout = findViewById(R.id.WeekSelection_DropMenu);
        autoCompleteTextView = findViewById(R.id.week_dropitems);
        program_details_textView = findViewById(R.id.ProgramDetails);

        String [] items = {"Week 1", "Week 2", "Week 3", "Week 4", "Week 5", "Week 6", "Week 7", "Week 8", "Week 9", "Week 10", "Week 11", "Week 12"};
        ArrayAdapter<String> itemAdapter=new ArrayAdapter<>(SelectWeek.this, R.layout.items_list, items);
        autoCompleteTextView.setAdapter(itemAdapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                program_details_textView.setText((String)parent.getItemAtPosition(position));
                if (position==0) {
                    Intent intent = new Intent(SelectWeek.this, Program_Week1.class);
                    startActivity(intent);
                }
                else if (position==1){
                    Intent intent = new Intent(SelectWeek.this, Program_Week2.class);
                    startActivity(intent);
                }
                else if (position==2){
                    Intent intent = new Intent(SelectWeek.this, Program_Week3.class);
                    startActivity(intent);
                }
                else if (position==3){
                    Intent intent = new Intent(SelectWeek.this, Program_Week4.class);
                    startActivity(intent);
                }
                else if (position==4){
                    Intent intent = new Intent(SelectWeek.this, Program_Week5.class);
                    startActivity(intent);
                }

                else if (position==5){
                    Intent intent = new Intent(SelectWeek.this, Program_Week6.class);
                    startActivity(intent);
                }

                else if (position==6){
                    Intent intent = new Intent(SelectWeek.this, Program_Week7.class);
                    startActivity(intent);
                }

                else if (position==7){
                    Intent intent = new Intent(SelectWeek.this, Program_Week8.class);
                    startActivity(intent);
                }

                else if (position==8){
                    Intent intent = new Intent(SelectWeek.this, Program_Week9.class);
                    startActivity(intent);
                }

                else if (position==9){
                    Intent intent = new Intent(SelectWeek.this, Program_Week10.class);
                    startActivity(intent);
                }

                else if (position==10){
                    Intent intent = new Intent(SelectWeek.this, Program_Week11.class);
                    startActivity(intent);
                }

                else if (position==11){
                    Intent intent = new Intent(SelectWeek.this, Program_Week12.class);
                    startActivity(intent);
                }

            }
        });

        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.Program_dash);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.RunningMetrics_dash:
                    startActivity(new Intent(getApplicationContext(), LifetimeMetrics.class));
                    overridePendingTransition(0, 0);
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