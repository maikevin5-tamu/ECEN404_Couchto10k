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
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RatingRun extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button submitRunBtn;

    private FirebaseAuth auth;

    private Spinner spinner_rating;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://couch-to-10k-testing-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_run);

        auth = FirebaseAuth.getInstance();

        submitRunBtn = findViewById(R.id.Submit_rr);

        spinner_rating = findViewById(R.id.rate_input_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Rating, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_rating.setAdapter(adapter);
        spinner_rating.setOnItemSelectedListener(this);



        submitRunBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_rating = spinner_rating.getSelectedItem().toString();


                if (txt_rating.isEmpty() ){
                    Toast.makeText(RatingRun.this, "Please provide a rating", Toast.LENGTH_SHORT).show();
                } else {

                    databaseReference.child("User ID").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                            //String txt_UID = currentFirebaseUser.getUid();
                            String txt_email_node = currentFirebaseUser.getEmail();

                            int iend = txt_email_node.indexOf("@");

                            String email_SS = "";
                            if (iend != -1) {
                                email_SS = txt_email_node.substring(0, iend); //this will give abc
                            }

                            databaseReference.child("User ID").child(email_SS).child("rating").setValue(txt_rating);
                            Intent intent = new Intent(RatingRun.this, MainMenu.class);
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