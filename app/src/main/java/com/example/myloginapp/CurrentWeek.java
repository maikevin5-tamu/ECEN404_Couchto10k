package com.example.myloginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CurrentWeek extends AppCompatActivity {

    TextInputLayout textInputLayout;
    AutoCompleteTextView autoCompleteTextView;

    private Button goto_week_btn;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_week);

        TextView program_details1_textView = findViewById(R.id.ProgramDetails1);
        TextView program_details2_textView = findViewById(R.id.ProgramDetails2);
        TextView program_details3_textView = findViewById(R.id.ProgramDetails3);

        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.Program_dash);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

                program_details1_textView.setText("");
                program_details2_textView.setText("");
                program_details3_textView.setText("");
                program_details1_textView.setTextColor(Color.parseColor("#000000"));
                program_details2_textView.setTextColor(Color.parseColor("#000000"));
                program_details3_textView.setTextColor(Color.parseColor("#000000"));

                FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                //String txt_UID = currentFirebaseUser.getUid();
                String txt_email_node = currentFirebaseUser.getEmail();

                int iend = txt_email_node.indexOf("@");

                String email_SS = "";
                if (iend != -1) {
                    email_SS = txt_email_node.substring(0, iend); //this will give abc
                }

                StorageReference storageReference1 = FirebaseStorage.getInstance().getReference().child(email_SS + "/Programs/CurrentWeek.json");
                storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        String url = uri.toString();

                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONArray jsonWeek = response.getJSONArray("CurrentWeek");
                                    for (int i = 0; i < jsonWeek.length(); i++) {
                                        JSONObject jsonObject1 = jsonWeek.getJSONObject(i);
                                        JSONArray jsonArray1 = jsonObject1.getJSONArray("Day 1");
                                        JSONArray jsonArray2 = jsonObject1.getJSONArray("Day 2");
                                        JSONArray jsonArray3 = jsonObject1.getJSONArray("Day 3");


                                        for (int j = 0; j < jsonArray1.length(); j++) {
                                            JSONObject jsonObject2 = jsonArray1.getJSONObject(j);

                                            String Day1 = jsonObject2.getString("Day 1");
                                            int complete = jsonObject2.getInt("Complete");

                                            program_details1_textView.append(Day1);
                                            if (complete == 1) {
                                                program_details1_textView.setTextColor(Color.parseColor("#738b28"));
                                            }

                                        }

                                        for (int k = 0; k < jsonArray2.length(); k++) {
                                            JSONObject jsonObject2 = jsonArray2.getJSONObject(k);

                                            String Day1 = jsonObject2.getString("Day 2");
                                            int complete = jsonObject2.getInt("Complete");

                                            program_details2_textView.append(Day1);
                                            if (complete == 1) {
                                                program_details2_textView.setTextColor(Color.parseColor("#738b28"));
                                            }

                                        }

                                        for (int l = 0; l < jsonArray3.length(); l++) {
                                            JSONObject jsonObject2 = jsonArray3.getJSONObject(l);

                                            String Day1 = jsonObject2.getString("Day 3");
                                            int complete = jsonObject2.getInt("Complete");

                                            program_details3_textView.append(Day1);
                                            if (complete == 1) {
                                                program_details3_textView.setTextColor(Color.parseColor("#738b28"));
                                            }

                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                        }
                                , new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(CurrentWeek.this, error.getMessage(), Toast.LENGTH_SHORT);
                            }

                        });

                        requestQueue.add(jsonObjectRequest);


                    }
                });
            }
    }

