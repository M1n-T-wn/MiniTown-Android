package com.example.minitown;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.minitown.login.ui.data.LoginResponse;
import com.example.minitown.login.ui.data.RegisterResponse;

public class MainPageActivity extends AppCompatActivity {
    LoginResponse loginResponse;
    TextView profile_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        profile_name = (TextView) findViewById(R.id.tv_profile_name);

        Intent intent = getIntent();
        if (intent.getExtras() != null){
            loginResponse = (LoginResponse) intent.getSerializableExtra("data");
            profile_name.setText(loginResponse.getName());
            Log.e("TAG", "=====>" + loginResponse.getId());

        }
    }
}