package com.example.minitown.login.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minitown.MainPageActivity;
import com.example.minitown.R;
import com.example.minitown.login.ui.data.ApiClient;
import com.example.minitown.login.ui.data.LoginRequest;
import com.example.minitown.login.ui.data.LoginResponse;
import com.example.minitown.login.ui.data.RegisterResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    TextView tv_go_signup;
    Button btn_login;
    EditText et_userId, et_userPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tv_go_signup = (TextView) findViewById(R.id.tv_make);
        btn_login = (Button) findViewById(R.id.btn_login);
        et_userId = (EditText) findViewById(R.id.et_id);
        et_userPassword = (EditText) findViewById(R.id.et_password);

        btn_login.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, MainPageActivity.class);
            startActivity(intent);
        });

        tv_go_signup.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_userId.getText().toString()) ||
                        TextUtils.isEmpty(et_userPassword.getText().toString())){
                    String message = "빈칸을 채워주세요";
                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();

                } else {
                    LoginRequest loginRequest = new LoginRequest();
                    loginRequest.setId(et_userId.getText().toString());
                    loginRequest.setPassword(et_userPassword.getText().toString());

                    loginUser(loginRequest);
                }
            }
        });
    }
    public void loginUser(LoginRequest loginRequest){
        Call<LoginResponse> loginResponseCall = ApiClient.getService().loginUser(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()){
                    LoginResponse loginResponse = response.body();
                    startActivity(new Intent(LoginActivity.this, MainPageActivity.class).putExtra("data", loginResponse));
                    finish();

                } else {
                    String message = "잠시 후 다시 시도해 주십시오";
                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}