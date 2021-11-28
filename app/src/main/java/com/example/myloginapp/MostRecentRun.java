package com.example.myloginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.URI;

public class MostRecentRun extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_most_recent_run);

        TextInputEditText BPMRun_TextInput = findViewById(R.id.BPM_Run);
        TextInputEditText BPMWalk_TextInput = findViewById(R.id.BPM_Walk);
        TextInputEditText Distance_TextInput = findViewById(R.id.Distance);
        TextInputEditText BrPM_TextInput = findViewById(R.id.BrPM);
        TextInputEditText Calories_TextInput = findViewById(R.id.Calories);

        Button button = findViewById(R.id.fetchData);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://api.npoint.io/3655039531492f7a283d";

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("metrics");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                int BPMRun = jsonObject.getInt("BPM Run");
                                int BPMWalk = jsonObject.getInt("BPM Walk");
                                int Distance = jsonObject.getInt("Distance");
                                int BrPM = jsonObject.getInt("BrPM");
                                int Calories = jsonObject.getInt("Calories");

                                BPMRun_TextInput.append(String.valueOf(BPMRun) + " BPM during Run (average)");
                                BPMWalk_TextInput.append(String.valueOf(BPMWalk) + " BPM during Walk (average)");
                                Distance_TextInput.append(String.valueOf(Distance) + " miles");
                                BrPM_TextInput.append(String.valueOf(BrPM) + " Breaths per Minute");
                                Calories_TextInput.append(String.valueOf(Calories) + " Calories Burned");


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MostRecentRun.this, error.getMessage(), Toast.LENGTH_SHORT);
                    }
                });

                requestQueue.add(jsonObjectRequest);

                }
            });

        bottomNavigationView = findViewById(R.id.bottom_navigator);
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