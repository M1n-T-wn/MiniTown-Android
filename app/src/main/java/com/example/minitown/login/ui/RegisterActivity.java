package com.example.minitown.login.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minitown.R;
import com.example.minitown.login.ui.data.ApiClient;
import com.example.minitown.login.ui.data.RegisterRequest;
import com.example.minitown.login.ui.data.RegisterResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterActivity extends AppCompatActivity {
    private EditText et_birth, et_gender, et_phone, et_name, et_id, et_password, et_address, et_detail;
    private Button btn_signup2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_birth = (EditText) findViewById(R.id.et_make_birth);
        et_gender = (EditText) findViewById(R.id.et_make_gender);
        et_phone = (EditText) findViewById(R.id.et_make_phone);
        et_name = (EditText) findViewById(R.id.et_make_name);
        et_id = (EditText) findViewById(R.id.et_make_id);
        et_password = (EditText) findViewById(R.id.et_make_password);
        et_address = (EditText) findViewById(R.id.et_make_address);
        et_detail = (EditText) findViewById(R.id.et_make_detail);
        btn_signup2 = (Button) findViewById(R.id.btn_signup2);

        btn_signup2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(et_birth.getText().toString()) ||
                        TextUtils.isEmpty(et_gender.getText().toString()) ||
                        TextUtils.isEmpty(et_phone.getText().toString()) ||
                        TextUtils.isEmpty(et_name.getText().toString()) ||
                        TextUtils.isEmpty(et_id.getText().toString()) ||
                        TextUtils.isEmpty(et_password.getText().toString()) ||
                        TextUtils.isEmpty(et_address.getText().toString()) ||
                        TextUtils.isEmpty(et_detail.getText().toString())){

                    String message = "빈칸을 채워주세요";
                    Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();


                } else {
                    RegisterRequest registerRequest = new RegisterRequest();
                    registerRequest.setId(et_birth.getText().toString());
                    registerRequest.setId(et_phone.getText().toString());
                    registerRequest.setId(et_name.getText().toString());
                    registerRequest.setId(et_id.getText().toString());
                    registerRequest.setPassword(et_password.getText().toString());
                    registerRequest.setId(et_address.getText().toString());
                    registerRequest.setId(et_detail.getText().toString());
                    registerUser(registerRequest);
                }
            }
        });

    }

    public void registerUser(RegisterRequest registerRequest) {
        Call<RegisterResponse> registerResponseCall = ApiClient.getService().registerUsers(registerRequest);
        registerResponseCall.enqueue(new Callback<RegisterResponse>() {

            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful()) {

                    String message = "성공";
                    Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();

                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finish();
                } else {
                    String message = "잠시 후 다시 시도해 주십시오";
                    Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
                    Log.v("success", "1");
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}
