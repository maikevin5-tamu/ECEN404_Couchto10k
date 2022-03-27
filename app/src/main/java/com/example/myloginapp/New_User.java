package com.example.myloginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class New_User extends AppCompatActivity {

    private Button registerBtn;
    private EditText email;
    private EditText password;
    private EditText con_password;

    private FirebaseAuth auth;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://couch-to-10k-testing-default-rtdb.firebaseio.com/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        email = findViewById(R.id.email_create);
        password = findViewById(R.id.password_create);
        con_password = findViewById(R.id.password_confirm);

        auth = FirebaseAuth.getInstance();

        registerBtn = findViewById(R.id.Register);


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email = email.getText().toString().trim();
                String txt_password = password.getText().toString();
                String txt_con_password = con_password.getText().toString();

                //FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
                //Toast.makeText(New_User.this, "" + currentFirebaseUser.getUid(), Toast.LENGTH_SHORT).show();
                //String txt_UID = currentFirebaseUser.getUid();

                //check empty fields
                if (txt_email.isEmpty() || txt_password.isEmpty()) {
                    Toast.makeText(New_User.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }

                //check passwords match
                else if (!txt_password.equals(txt_con_password)){
                    Toast.makeText(New_User.this, "Passwords are not matching", Toast.LENGTH_SHORT).show();
                }

                else{
                    databaseReference.child("User ID").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            registerUser(txt_email, txt_password);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

            }
        });
    }

    private void registerUser(String email, String password) {

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(New_User.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    databaseReference.child("User ID").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                            //String txt_UID = currentFirebaseUser.getUid();
                            String txt_email_node = currentFirebaseUser.getEmail();

                            int iend = txt_email_node.indexOf("@");

                            String email_SS = "";
                            if (iend != -1)
                            {
                                email_SS = txt_email_node.substring(0 , iend); //this will give abc
                            }


                            //databaseReference.child("User ID").child(txt_UID).child("email").setValue(email);
                            databaseReference.child("User ID").child(email_SS).child("password").setValue(password);


                            Toast.makeText(New_User.this, "User registered successfully.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(New_User.this, PhysicalParameters.class);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    Toast.makeText(New_User.this, "Registering user successful", Toast.LENGTH_SHORT).show();
                }else {

                    Toast.makeText(New_User.this, "Failed Registration: " + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
