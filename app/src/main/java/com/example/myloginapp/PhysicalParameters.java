package com.example.myloginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PhysicalParameters extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button button;

    private TextView textView1; //height
    private TextView textView2; //weight

    private NumberPicker numberPicker1; //height
    private NumberPicker numberPicker2; //weight

    private Spinner spinner_sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physical_parameters);

        button = findViewById(R.id.Submit_PP); //register to welcome
        textView1 = (TextView) findViewById(R.id.Height);
        numberPicker1 = (NumberPicker) findViewById(R.id.Height_numberpicker);

        numberPicker1.setMaxValue(80);
        numberPicker1.setMinValue(0);
        numberPicker1.setValue(60);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PhysicalParameters.this, Cto10k_Context.class);
                startActivity(intent);
            }
        });

        numberPicker1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker1, int oldValue, int newValue) {
                textView1.setText("Height (in.): " + newValue);
            }
        });

        textView2 = (TextView) findViewById(R.id.Weight);
        numberPicker2 = (NumberPicker) findViewById(R.id.Weight_numberpicker);

        numberPicker2.setMaxValue(300);
        numberPicker2.setMinValue(0);
        numberPicker2.setValue(160);

        numberPicker2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker2, int oldValue, int newValue) {
                textView2.setText("Weight (lbs): " + newValue);
            }
        });

        spinner_sex = findViewById(R.id.spinner_sex);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Sex, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_sex.setAdapter(adapter);
        spinner_sex.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}