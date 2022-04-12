package com.example.myloginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Cto10k_Context extends AppCompatActivity {

    private Button button;

    StorageReference firebaseStorage1 = FirebaseStorage.getInstance().getReferenceFromUrl("gs://couch-to-10k-testing.appspot.com/");

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

        firebaseStorage1.child(email_SS);

        button = findViewById(R.id.next_cto10k);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(Cto10k_Context.this, MainMenu.class);
                startActivity(intent);
            }
        });
    }
}