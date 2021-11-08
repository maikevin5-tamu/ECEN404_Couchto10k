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

public class PhysicalParameteres2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button button; //submit button

    private NumberPicker numberPicker1; //age number picker
    private Spinner spinner_sex;
    private TextView textView1; //age text

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physical_parameteres2);

        button = findViewById(R.id.Submit_PP); //register to welcome
        textView1 = (TextView) findViewById(R.id.Age);
        numberPicker1 = (NumberPicker) findViewById(R.id.Age_numberpicker);

        numberPicker1.setMaxValue(100);
        numberPicker1.setMinValue(0);
        numberPicker1.setValue(25);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PhysicalParameteres2.this, Cto10k_Context.class);
                startActivity(intent);
            }
        });

        numberPicker1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker1, int oldValue, int newValue) {
                textView1.setText("Age: " + newValue);
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