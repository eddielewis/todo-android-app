package com.example.secondapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private static final String LOGIN_URL = "http://ed8.localhost.run/api/v1/rest-auth/login/";
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void attemptLogin(View view) {
        EditText usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        EditText passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        String email = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        Context context = getApplicationContext();
        CharSequence text = "Logging in...";
        int duration = Toast.LENGTH_SHORT;
        Toast.makeText(context, text, duration).show();

        sendLoginRequest(email,password);
    }

    public void sendLoginRequest(final String username, final String password) {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        onLoginSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                CharSequence errorMessage = "There was a problem logging in.";

                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    errorMessage = "Please check your internet connection";
                } else if (error instanceof AuthFailureError) {
                    errorMessage = "Username or password incorrect, please try again.";
                } else if (error instanceof ServerError) {
                    try {
                        String responseBody = new String(error.networkResponse.data, "utf-8");
                        JSONObject obj = null;
                        try {
                            obj = new JSONObject(responseBody);
                            errorMessage = obj.getJSONArray("non_field_errors").getString(0);
                        } catch (JSONException e) {
                            Log.e(TAG, "Cannot parse JSON server response to Java object");
                        }
                    } catch (UnsupportedEncodingException errorr) {
                    }
                } else if (error instanceof NetworkError) {
                    errorMessage = "Please check your internet connection";
                } else if (error instanceof ParseError) {
                    Log.e(TAG, "Response could not be parsed");
                }

                Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };
        queue.add(stringRequest);
    }

    private void onLoginSuccess(String response) {
        JSONObject obj = null;
        String token = "";
        try {
            obj = new JSONObject(response);
            token = obj.getString("key");
        } catch (JSONException e) {
            Log.e(TAG, "Cannot parse JSON server response to Java object");
        }
        if (storeToken(token)) {
            Intent intent = new Intent(this, TodoListActivity.class);
            startActivity(intent);
        } else {
            Log.e(TAG, "Storing auth token in SharedPreferences failed");
        }
    }

    public boolean storeToken(String token) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("com.example.secondapp", Context.MODE_PRIVATE);
        return pref.edit().putString("auth_token", token).commit();
    }
}

/*
url: `http://127.0.0.1:8000/api/v1/${id}`,
headers: { 'Authorization': `Token ${window.sessionStorage.getItem("key")}` },
*/