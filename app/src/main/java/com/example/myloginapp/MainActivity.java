package com.example.myloginapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private Button loginBtn;
    private Button NU_btn;
    private EditText email;
    private EditText password;


    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.email_signin);
        password = findViewById(R.id.password_signin);
        loginBtn = findViewById(R.id.loginbtn);

        auth = FirebaseAuth.getInstance();




        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_email = email.getText().toString().trim();
                String txt_password = password.getText().toString();
                if (txt_email.isEmpty() || txt_password.isEmpty() ) {
                    Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    loginUser(txt_email, txt_password);
                }
            }
        });

        NU_btn = findViewById(R.id.newuser);

        NU_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, New_User.class);
                startActivity(intent);
            }
        });

    }


    private void loginUser(String email, String password) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    //Toast.makeText(PhysicalParameters3.this, "" + currentFirebaseUser.getUid(), Toast.LENGTH_SHORT).show();
                    String txt_UID = currentFirebaseUser.getUid();

                    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                    rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.child("User ID").child(txt_UID).hasChild("height")) {
                                String compare = Objects.requireNonNull(snapshot.child("User ID").child(txt_UID).child("sex").getValue()).toString();
                                if (compare.equals("Male")) {
                                    if (snapshot.child("User ID").child(txt_UID).hasChild("medical conditions")) {
                                        Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(MainActivity.this, MainMenu.class);
                                        startActivity(intent);
                                    }
                                    else {
                                        Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(MainActivity.this, PhysicalParameters4.class);
                                        startActivity(intent);
                                    }
                                }
                                else {
                                    if (snapshot.child("User ID").child(txt_UID).hasChild("medical conditions")) {
                                        Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(MainActivity.this, MainMenu.class);
                                        startActivity(intent);
                                    }
                                    else {
                                        Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(MainActivity.this, PhysicalParameters3.class);
                                        startActivity(intent);
                                    }
                                }
                            }
                            else {
                                Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this, PhysicalParameters.class);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
                else{
                    Toast.makeText(MainActivity.this, "Login Failed: " + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
