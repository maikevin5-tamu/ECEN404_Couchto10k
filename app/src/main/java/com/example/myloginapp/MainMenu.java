package com.example.myloginapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainMenu extends AppCompatActivity {

    private Button button;

    BottomNavigationView bottomNavigationView;
    TextView title;

    Button alertButton;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        button = findViewById(R.id.Program_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenu.this, MainProgramModule.class);
                startActivity(intent);
            }
        });

        button = findViewById(R.id.RunningMetrics_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenu.this, LifetimeMetrics.class);
                startActivity(intent);
            }
        });

        title = findViewById(R.id.title);
        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.Home_dash);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.RunningMetrics_dash:
                    startActivity(new Intent(getApplicationContext(), LifetimeMetrics.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.Home_dash:
                    return true;
                case R.id.Program_dash:
                    startActivity(new Intent(getApplicationContext(), MainProgramModule.class));
                    overridePendingTransition(0, 0);
                    return true;
            }

            return true;

        });

        alertButton = findViewById(R.id.alertButton);
        builder = new AlertDialog.Builder(this);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        alertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://api.npoint.io/46f5ca0fab34ed2a17ec";
                final String[] text = new String[1];
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonArray = response.getJSONArray("alert");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                text[0] = jsonObject.getString("Text");

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        builder.setTitle("Updates")
                                .setMessage(text[0])
                                .setCancelable(true)
                                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                })
                                .setPositiveButton("Next", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface1, int i) {
                                        builder.setTitle("Performance Analysis")
                                                .setMessage("To help us analyze your performance, please rate your most recent run.")
                                                .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface1, int i) {
                                                        Intent intent = new Intent(MainMenu.this, RatingRun.class);
                                                        startActivity(intent);
                                                    }
                                                });
                                        builder.show();
                                    }

                                })
                                .show();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainMenu.this, error.getMessage(), Toast.LENGTH_SHORT);
                    }
                });

                requestQueue.add(jsonObjectRequest);

            }
        });
    }
}