package com.example.myloginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PhysicalParameters extends New_User implements AdapterView.OnItemSelectedListener {

    private Button button;

    private EditText height;
    private EditText weight;
    private EditText age;
    private Spinner spinner_sex;
    private EditText userID;


    private FirebaseAuth auth;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://couch-to-10k-testing-default-rtdb.firebaseio.com/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physical_parameters);

        button = findViewById(R.id.Next_PP); //register to welcome
        height = findViewById(R.id.Height);
        weight = findViewById(R.id.Weight);
        age = findViewById(R.id.Age);

        userID = findViewById(R.id.userID);

        spinner_sex = findViewById(R.id.spinner_sex);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Sex, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_sex.setAdapter(adapter);
        spinner_sex.setOnItemSelectedListener(this);

        auth = FirebaseAuth.getInstance();

        FirebaseUser firebaseUser = auth.getCurrentUser();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_height = height.getText().toString();
                String txt_weight = weight.getText().toString();
                String txt_age = age.getText().toString();
                String txt_userID = userID.getText().toString();


                if (txt_height.isEmpty() || txt_weight.isEmpty() || txt_age.isEmpty()) {
                    Toast.makeText(PhysicalParameters.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    databaseReference.child("User ID").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            databaseReference.child("User ID").child(txt_userID).child("height").setValue(txt_height);
                            databaseReference.child("User ID").child(txt_userID).child("weight").setValue(txt_weight);
                            databaseReference.child("User ID").child(txt_userID).child("age").setValue(txt_age);
                            Intent intent = new Intent(PhysicalParameters.this, PhysicalParameteres2.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}