package com.example.minitown.login.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minitown.MainPageActivity;
import com.example.minitown.R;
import com.example.minitown.login.ui.data.ApiClient;
import com.example.minitown.login.ui.data.LoginRequest;
import com.example.minitown.login.ui.data.TokenResponse;
import com.example.minitown.user.ProfileRequest;

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
            Intent intent = new Intent(LoginActivity.this, SendSmsActivity.class);
            startActivity(intent);
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_userId.getText().toString()) ||
                        TextUtils.isEmpty(et_userPassword.getText().toString())) {
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

    public void loginUser(LoginRequest loginRequest) {
        Call<TokenResponse> loginResponseCall = ApiClient.getService().loginUser(loginRequest);
        loginResponseCall.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        TokenResponse tokenResponse = response.body();
                        Log.d("DebugLog", "LoginResponse: " + tokenResponse.getAccessToken());

                        SharedPreferences.Editor editor = getSharedPreferences("token", MODE_PRIVATE).edit();
                        editor.putString("access", response.body().getAccessToken());
                        editor.putString("refresh", response.body().getRefreshToken());
                        editor.apply();

                        startActivity(new Intent(LoginActivity.this, MainPageActivity.class));
                        finish();

                    } else {
                        // 예외처리 하기
                    }
                } else {
                    String message = "잠시 후 다시 시도해 주십시오";
                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}