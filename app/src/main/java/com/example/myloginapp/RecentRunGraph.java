package com.example.myloginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class RecentRunGraph extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_run_graph);

        bottomNavigationView = findViewById(R.id.bottom_navigator);

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        //String txt_UID = currentFirebaseUser.getUid();
        String txt_email_node = currentFirebaseUser.getEmail();

        int iend = txt_email_node.indexOf("@");

        String email_SS = "";
        if (iend != -1) {
            email_SS = txt_email_node.substring(0, iend); //this will give abc
        }

        StorageReference storageReference1 = FirebaseStorage.getInstance().getReference().child(email_SS + "/RunningMetrics/BPMvSpeed.png");

        try {
            final File bpmvsspeed = File.createTempFile("bpmvsspeed", "png");
            storageReference1.getFile(bpmvsspeed)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                            Bitmap bitmap = BitmapFactory.decodeFile(bpmvsspeed.getAbsolutePath());
                            ((ImageView) findViewById(R.id.plot)).setImageBitmap(bitmap);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
    } catch (IOException e) {
            e.printStackTrace();
        }

        bottomNavigationView.setSelectedItemId(R.id.RunningMetrics_dash);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.RunningMetrics_dash:
                    startActivity(new Intent(getApplicationContext(), LifetimeMetrics.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.Home_dash:
                    startActivity(new Intent(getApplicationContext(), MainMenu.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.Program_dash:
                    startActivity(new Intent(getApplicationContext(), MainProgramModule.class));
                    overridePendingTransition(0, 0);
                    return true;
            }

            return true;
        });

    }
}
