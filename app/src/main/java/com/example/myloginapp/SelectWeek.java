package com.example.myloginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

public class SelectWeek extends AppCompatActivity {

    TextInputLayout textInputLayout;
    AutoCompleteTextView autoCompleteTextView;

    private Button goto_week_btn;

    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_week);


        textInputLayout = findViewById(R.id.WeekSelection_DropMenu);
        autoCompleteTextView = findViewById(R.id.week_dropitems);
        TextView program_details1_textView = findViewById(R.id.ProgramDetails1);
        TextView program_details2_textView = findViewById(R.id.ProgramDetails2);
        TextView program_details3_textView = findViewById(R.id.ProgramDetails3);

        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.Program_dash);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String [] items = {"Week 1", "Week 2", "Week 3", "Week 4", "Week 5", "Week 6", "Week 7", "Week 8", "Week 9", "Week 10", "Week 11", "Week 12"};
        ArrayAdapter<String> itemAdapter=new ArrayAdapter<>(SelectWeek.this, R.layout.items_list, items);
        autoCompleteTextView.setAdapter(itemAdapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //resets text and text color
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

                StorageReference storageReference1 = FirebaseStorage.getInstance().getReference().child(email_SS + "/Programs/TotalProgram.json");
                //StorageReference storageReference1 = FirebaseStorage.getInstance().getReference().child(email_SS + "/test_totalprogram.json");
                storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        String url = uri.toString();



                //String url = "https://api.npoint.io/b1c135e19ff0a4ca5205";
                if (position == 0) {
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray jsonWeek = response.getJSONArray("1");
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
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(SelectWeek.this, error.getMessage(), Toast.LENGTH_SHORT);
                        }

                    });

                    requestQueue.add(jsonObjectRequest);

                    return;

                }

                else if (position == 1) {
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray jsonWeek = response.getJSONArray("2");
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
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(SelectWeek.this, error.getMessage(), Toast.LENGTH_SHORT);
                        }

                    });

                    requestQueue.add(jsonObjectRequest);

                    return;

                }

                else if (position == 2) {
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray jsonWeek = response.getJSONArray("3");
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
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(SelectWeek.this, error.getMessage(), Toast.LENGTH_SHORT);
                        }

                    });

                    requestQueue.add(jsonObjectRequest);

                    return;

                }

                else if (position == 3) {
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray jsonWeek = response.getJSONArray("4");
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
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(SelectWeek.this, error.getMessage(), Toast.LENGTH_SHORT);
                        }

                    });

                    requestQueue.add(jsonObjectRequest);

                    return;

                }

                else if (position == 4) {
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray jsonWeek = response.getJSONArray("5");
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
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(SelectWeek.this, error.getMessage(), Toast.LENGTH_SHORT);
                        }

                    });

                    requestQueue.add(jsonObjectRequest);

                    return;

                }

                else if (position == 5) {
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray jsonWeek = response.getJSONArray("6");
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
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(SelectWeek.this, error.getMessage(), Toast.LENGTH_SHORT);
                        }

                    });

                    requestQueue.add(jsonObjectRequest);

                    return;

                }

                else if (position == 6) {
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray jsonWeek = response.getJSONArray("7");
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
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(SelectWeek.this, error.getMessage(), Toast.LENGTH_SHORT);
                        }

                    });

                    requestQueue.add(jsonObjectRequest);

                    return;

                }

                else if (position == 7) {
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray jsonWeek = response.getJSONArray("8");
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
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(SelectWeek.this, error.getMessage(), Toast.LENGTH_SHORT);
                        }

                    });

                    requestQueue.add(jsonObjectRequest);

                    return;

                }

                else if (position == 8) {
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray jsonWeek = response.getJSONArray("9");
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
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(SelectWeek.this, error.getMessage(), Toast.LENGTH_SHORT);
                        }

                    });

                    requestQueue.add(jsonObjectRequest);

                    return;

                }

                else if (position == 9) {
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray jsonWeek = response.getJSONArray("10");
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
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(SelectWeek.this, error.getMessage(), Toast.LENGTH_SHORT);
                        }

                    });

                    requestQueue.add(jsonObjectRequest);

                    return;

                }

                else if (position == 10) {
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray jsonWeek = response.getJSONArray("11");
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
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(SelectWeek.this, error.getMessage(), Toast.LENGTH_SHORT);
                        }

                    });

                    requestQueue.add(jsonObjectRequest);

                    return;

                }

                else if (position == 11) {
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray jsonWeek = response.getJSONArray("12");
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
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(SelectWeek.this, error.getMessage(), Toast.LENGTH_SHORT);
                        }

                    });

                    requestQueue.add(jsonObjectRequest);

                    return;

                }


            }

        });
            }
        });


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
