package com.example.myloginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class New_User extends AppCompatActivity {

    private Button button_reg;

    private FirebaseAuth mAuth;

    TextInputEditText email_create;
    TextInputEditText password_create;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);


        email_create = findViewById(R.id.email_create);
        password_create = findViewById(R.id.password_create);
        mAuth = FirebaseAuth.getInstance();

        button_reg = findViewById(R.id.Register); //register to welcome

        button_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(New_User.this, Welcome_Page.class);
                startActivity(intent);
            }
        });
/**
        button_reg.setOnClickListener(view -> {
            createUser();
        });
    }

    private void createUser() {
        String email = email_create.getText().toString();
        String password = password_create.getText().toString();

        if (TextUtils.isEmpty(email)) {
            email_create.setError("Email cannot be empty");
            email_create.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            password_create.setError("Password cannot be empty");
            password_create.requestFocus();
        } else {
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(New_User.this, "User registered successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(New_User.this, Welcome_Page.class));
                    }
                    else {
                        Toast.makeText(New_User.this, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
**/

}
}