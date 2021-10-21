package com.example.minitown.login.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.minitown.MainPageActivity;
import com.example.minitown.R;

public class LoginActivity extends AppCompatActivity {
    TextView textView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        textView = (TextView) findViewById(R.id.tv_make);
        button = (Button) findViewById(R.id.btn_login);

        button.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, MainPageActivity.class);
            startActivity(intent);
        });

        textView.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}