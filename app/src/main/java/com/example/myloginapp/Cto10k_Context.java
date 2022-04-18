package com.example.myloginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.internal.StorageReferenceUri;

public class Cto10k_Context extends AppCompatActivity {

    private Button button;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://couch-to-10k-testing-default-rtdb.firebaseio.com/");

    StorageReference firebaseStorage1 = FirebaseStorage.getInstance().getReferenceFromUrl("gs://couch-to-10k-testing.appspot.com");

    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cto10k_context);

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        //String txt_UID = currentFirebaseUser.getUid();
        String txt_email_node = currentFirebaseUser.getEmail();

        int iend = txt_email_node.indexOf("@");

        String email_SS = "";
        if (iend != -1)
        {
            email_SS = txt_email_node.substring(0 , iend); //this will give abc
        }



        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                //Toast.makeText(PhysicalParameters3.this, "" + currentFirebaseUser.getUid(), Toast.LENGTH_SHORT).show();
                String txt_UID = currentFirebaseUser.getUid();
                //String txt_UID = currentFirebaseUser.getUid();
                String txt_email_node = currentFirebaseUser.getEmail();

                int iend = txt_email_node.indexOf("@");

                String email_SS = "";
                if (iend != -1) {
                    email_SS = txt_email_node.substring(0, iend); //this will give abc
                }

                String string1 = (String) snapshot.child("User ID").child(email_SS).child("RE").getValue();

                button = findViewById(R.id.next_cto10k);

                if (string1.equals("Beginner")) {
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(Cto10k_Context.this, BeginnerProgram.class);
                            startActivity(intent);
                        }
                    });
                }

                else {
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(Cto10k_Context.this, IntAdvProgram.class);
                            startActivity(intent);
                        }
                    });
                }



/***
                if (rootRef.child("User ID").child(email_SS).child("week").equalTo(1)) {
                    button = findViewById(R.id.next_cto10k);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            Intent intent = new Intent(Cto10k_Context.this, BeginnerProgram.class);
                            startActivity(intent);
                        }
                    });
                }

                else {
                    button = findViewById(R.id.next_cto10k);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            Intent intent = new Intent(Cto10k_Context.this, IntAdvProgram.class);
                            startActivity(intent);
                        }
                    });
                }


            }


        *///


        }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}