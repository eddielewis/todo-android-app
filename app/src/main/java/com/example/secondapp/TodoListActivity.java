package com.example.secondapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TodoListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        Intent intent = getIntent();
        TextView emailView = findViewById(R.id.emailView);
        TextView textView = findViewById(R.id.passwordView);

        //SharedPreferences pref = getApplicationContext().getSharedPreferences("com.example.secondapp", Context.MODE_PRIVATE);
        //emailView.setText(pref.getString("auth_token", new String()));
    }
}
