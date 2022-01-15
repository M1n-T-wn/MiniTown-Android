package com.example.minitown;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.minitown.login.ui.data.ApiClient;
import com.example.minitown.login.ui.data.TokenResponse;
import com.example.minitown.user.ProfileResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPageActivity extends AppCompatActivity {
    private TokenResponse loginResponse;
    private TextView profile_name;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        profile_name = (TextView) findViewById(R.id.tv_profile_name);

        loginResponse = new TokenResponse();
        sharedPreferences = getSharedPreferences("token", MODE_PRIVATE);
        ProfileResponse profileResponse = new ProfileResponse();
        String name = profileResponse.getName(profileResponse);

        ApiClient.getService().profile(profileResponse).enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {

                profile_name.setText(name);
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {

            }
        });
        String accessToken = sharedPreferences.getString("accessToken", "");
        String refreshToken = sharedPreferences.getString("refreshToken", "");

        if (accessToken != null && !accessToken.isEmpty()) {
            loginResponse.setAccessToken(accessToken);
        }

        if (refreshToken != null && !refreshToken.isEmpty()) {
            loginResponse.setRefreshToken(refreshToken);
        }
    }
}