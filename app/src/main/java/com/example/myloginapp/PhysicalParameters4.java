package com.example.myloginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PhysicalParameters4 extends AppCompatActivity {


    private Button button;

    ListView listViewData;
    ArrayAdapter<String> adapter;
    String [] med_conditions = {"Respiratory Disease", "Cardiovascular Disease", "None"};

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://couch-to-10k-testing-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physical_parameters3);

        button = findViewById(R.id.Submit_PP); //PP4 to welcome

        listViewData = findViewById(R.id.listView_data);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, med_conditions);
        listViewData.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                //Toast.makeText(PhysicalParameters3.this, "" + currentFirebaseUser.getUid(), Toast.LENGTH_SHORT).show();
                String txt_UID = currentFirebaseUser.getUid();
                //String txt_UID = currentFirebaseUser.getUid();
                String txt_email_node = currentFirebaseUser.getEmail();

                int iend = txt_email_node.indexOf("@");

                String email_SS = "";
                if (iend != -1)
                {
                    email_SS = txt_email_node.substring(0 , iend); //this will give abc
                }

                SparseBooleanArray checked = listViewData.getCheckedItemPositions();
                for (int j = 0; j < listViewData.getCount(); j++) {
                    if (checked.get(j)) {
                        String item = med_conditions[j];
                        if (j == 0) {
                            databaseReference.child("User ID").child(email_SS).child("medical conditions").child("Respiratory Disease").setValue(1);
                        }
                        else if (j == 1) {
                            databaseReference.child("User ID").child(email_SS).child("medical conditions").child("Cardiovascular Disease").setValue(2);
                        }
                        else if (j == 2) {
                            databaseReference.child("User ID").child(email_SS).child("medical conditions").child("Pregnancy").setValue(0);
                        }
                    }
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
                        if (iend != -1)
                        {
                            email_SS = txt_email_node.substring(0 , iend); //this will give abc
                        }

                        if (snapshot.child("User ID").child(email_SS).child("medical conditions").hasChild("None") && (snapshot.child("User ID").child(txt_UID).child("medical conditions").hasChild("Respiratory Disease") || snapshot.child("User ID").child(txt_UID).child("medical conditions").hasChild("Cardiovascular Disease"))) {
                            Toast.makeText(PhysicalParameters4.this, "Invalid inputs, please try again", Toast.LENGTH_SHORT).show();
                            rootRef.child("User ID").child(email_SS).child("medical conditions").removeValue();
                        }

                        else if (snapshot.child("User ID").child(email_SS).child("medical conditions").hasChild("Respiratory Disease") && (snapshot.child("User ID").child(email_SS).child("medical conditions").hasChild("Cardiovascular Disease"))) {
                            Long heartrate_RT = (Long) snapshot.child("User ID").child(email_SS).child("Max HR").getValue();
                            String HR_string = heartrate_RT.toString();
                            int HR_int = Integer.parseInt(HR_string);
                            int heartrate_new = HR_int - 25;
                            databaseReference.child("User ID").child(email_SS).child("Max HR").setValue(heartrate_new);
                            databaseReference.child("User ID").child(email_SS).child("medical conditions").child("Pregnancy").setValue(0);
                            Intent intent = new Intent(PhysicalParameters4.this, ExperiencePP.class);
                            startActivity(intent);
                        }

                        else if (snapshot.child("User ID").child(email_SS).child("medical conditions").hasChild("Respiratory Disease")) {
                            Long heartrate_RT = (Long) snapshot.child("User ID").child(email_SS).child("Max HR").getValue();
                            String HR_string = heartrate_RT.toString();
                            int HR_int = Integer.parseInt(HR_string);
                            int heartrate_new = HR_int - 10;
                            databaseReference.child("User ID").child(email_SS).child("Max HR").setValue(heartrate_new);
                            databaseReference.child("User ID").child(email_SS).child("medical conditions").child("Cardiovascular Disease").setValue(0);
                            databaseReference.child("User ID").child(email_SS).child("medical conditions").child("Pregnancy").setValue(0);
                            Intent intent = new Intent(PhysicalParameters4.this, ExperiencePP.class);
                            startActivity(intent);
                        }

                        else if (snapshot.child("User ID").child(email_SS).child("medical conditions").hasChild("Cardiovascular Disease")) {
                            Long heartrate_RT = (Long) snapshot.child("User ID").child(email_SS).child("Max HR").getValue();
                            String HR_string = heartrate_RT.toString();
                            int HR_int = Integer.parseInt(HR_string);
                            int heartrate_new = HR_int - 15;
                            databaseReference.child("User ID").child(email_SS).child("Max HR").setValue(heartrate_new);
                            databaseReference.child("User ID").child(email_SS).child("medical conditions").child("Respiratory Disease").setValue(0);
                            databaseReference.child("User ID").child(email_SS).child("medical conditions").child("Pregnancy").setValue(0);
                            Intent intent = new Intent(PhysicalParameters4.this, ExperiencePP.class);
                            startActivity(intent);
                        }

                        else {
                            databaseReference.child("User ID").child(email_SS).child("medical conditions").child("Respiratory Disease").setValue(0);
                            databaseReference.child("User ID").child(email_SS).child("medical conditions").child("Cardiovascular Disease").setValue(0);
                            databaseReference.child("User ID").child(email_SS).child("medical conditions").child("Pregnancy").setValue(0);
                            Intent intent = new Intent(PhysicalParameters4.this, ExperiencePP.class);
                            startActivity(intent);
                        }

/***
                        if (snapshot.child("User ID").child(email_SS).child("medical conditions").hasChild("None") && (snapshot.child("User ID").child(txt_UID).child("medical conditions").hasChild("Respiratory Disease") || snapshot.child("User ID").child(txt_UID).child("medical conditions").hasChild("Cardiovascular Disease"))) {
                            Toast.makeText(PhysicalParameters4.this, "Invalid inputs, please try again", Toast.LENGTH_SHORT).show();
                            rootRef.child("User ID").child(email_SS).child("medical conditions").removeValue();
                        }
                        else if (!(snapshot.child("User ID").child(email_SS).child("medical conditions").hasChild("Respiratory Disease"))) {
                            databaseReference.child("User ID").child(email_SS).child("medical conditions").child("Respiratory Disease").setValue(0);
                        }
                        else if (!(snapshot.child("User ID").child(email_SS).child("medical conditions").hasChild("Cardiovascular Disease"))) {
                            databaseReference.child("User ID").child(email_SS).child("medical conditions").child("Cardiovascular Disease").setValue(0);
                        }
                        else {
                            databaseReference.child("User ID").child(email_SS).child("medical conditions").child("Pregnancy").setValue(0);
                            Intent intent = new Intent(PhysicalParameters4.this, ExperiencePP.class);
                            startActivity(intent);
                        }

***/
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_done){
            String itemSelected = "Selected items: \n";
            for (int i=0; i < listViewData.getCount();i++) {
                if (listViewData.isItemChecked(i)) {
                    itemSelected += listViewData.getItemAtPosition(i) + "\n";
                }
            }
            Toast.makeText(this, itemSelected, Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}