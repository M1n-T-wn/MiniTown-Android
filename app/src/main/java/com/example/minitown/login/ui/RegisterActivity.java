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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.minitown.R;
import com.example.minitown.login.ui.data.ApiClient;
import com.example.minitown.login.ui.data.ReceiveRandomRequest;
import com.example.minitown.login.ui.data.ReceiverRequest;
import com.example.minitown.login.ui.data.ReceiverResponse;
import com.example.minitown.login.ui.data.RegisterRequest;
import com.example.minitown.login.ui.data.TokenResponse;
import com.example.minitown.login.ui.data.UserService;

import java.lang.reflect.Parameter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Query;


public class RegisterActivity extends AppCompatActivity {
    private EditText et_birth, et_phone, et_name, et_id, et_password, et_address, et_detail, et_receive_sms;
    private Button btn_signup2, btn_serch_address, btn_receive_sms;
    private RadioGroup rg_gender;
    private RadioButton rbtn_man, rbtn_woman;
    private String checkMen, checkWomen;
    private SharedPreferences sharedPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_birth = (EditText) findViewById(R.id.et_make_birth);
        et_phone = (EditText) findViewById(R.id.et_make_phone);
        et_receive_sms = (EditText) findViewById(R.id.et_make_receive_sms);
        et_name = (EditText) findViewById(R.id.et_make_name);
        et_id = (EditText) findViewById(R.id.et_make_id);
        et_password = (EditText) findViewById(R.id.et_make_password);
        et_address = (EditText) findViewById(R.id.et_make_address);
        et_detail = (EditText) findViewById(R.id.et_make_detail);
        rg_gender = (RadioGroup) findViewById(R.id.rg_make_gender);
        rbtn_man = (RadioButton) findViewById(R.id.rbtn_man);
        rbtn_woman = (RadioButton) findViewById(R.id.rbtn_woman);
        btn_signup2 = (Button) findViewById(R.id.btn_signup2);
        btn_serch_address = (Button) findViewById(R.id.btn_search_address);
        btn_receive_sms = (Button) findViewById(R.id.btn_receive_sms);

        Intent intent = new Intent(this, SendSmsActivity.class);
        intent = getIntent();
        if (intent.getAction().equals("sendAction")){

            String msg = (String) intent.getExtras().get("phone");

            et_phone.setText(msg);

        }

        rg_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbtn_man){
                    checkMen = rbtn_man.getText().toString();
                } else if (checkedId == R.id.rbtn_woman){
                    checkWomen = rbtn_woman.getText().toString();
                }
            }
        });

        btn_receive_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent phone_num = getIntent();
                if (phone_num.getAction().equals("sendAction")){
                    String phone_Num = (String) phone_num.getExtras().get("phone");
                    et_phone.setText(phone_Num);
                }
                String phoneNum = et_phone.getText().toString();
                String rand = et_receive_sms.getText().toString();
                Intent send_intent = new Intent(RegisterActivity.this, ApiClient.class);
                ReceiverRequest receiverRequest = new ReceiverRequest();
                ReceiveRandomRequest randomRequest = new ReceiveRandomRequest();
                receiverRequest.setPhoneNum(phoneNum);
                randomRequest.setRand(rand);
                ApiClient.getService().receiver(receiverRequest, randomRequest).enqueue(new Callback<ReceiverResponse>() {
                    @Override
                    public void onResponse(Call<ReceiverResponse> call, Response<ReceiverResponse> response) {
                        Log.d("DebugLog", "Success : ");
                        Toast.makeText(getApplicationContext(), rand,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ReceiverResponse> call, Throwable t) {
                        Log.d("DebugLog", "Fail");
                        Toast.makeText(getApplicationContext(), "인증 실",Toast.LENGTH_SHORT).show();
                    }
                });






            }
        });



        btn_serch_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginAdressActivity.class);
                startActivity(intent);
            }
        });

        btn_signup2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterRequest registerRequest = new RegisterRequest();

                if (checkMen != null){
                    registerRequest.setGender(rbtn_man.getText().toString());
                } else if (checkWomen != null){
                    registerRequest.setGender(rbtn_woman.getText().toString());
                }

                if (TextUtils.isEmpty(et_birth.getText().toString()) ||
                        TextUtils.isEmpty(et_name.getText().toString()) ||
                        TextUtils.isEmpty(et_id.getText().toString()) ||
                        TextUtils.isEmpty(et_password.getText().toString()) ||
                        TextUtils.isEmpty(et_address.getText().toString()) ||
                        TextUtils.isEmpty(et_detail.getText().toString())){

                    String message = "빈칸을 채워주세요";
                    Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();


                } else {

                    registerRequest.setId(et_birth.getText().toString());
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
        Call<TokenResponse> registerResponseCall = ApiClient.getService().registerUsers(registerRequest);
        registerResponseCall.enqueue(new Callback<TokenResponse>() {

            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                if (response.isSuccessful()) {
                    String message = "성공";
                    Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();

                    sharedPreferences = getSharedPreferences("token", MODE_PRIVATE);

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    if (response.body() != null) {
                        editor.putString("access", response.body().getAccessToken());
                        editor.putString("refresh", response.body().getRefreshToken());
                        editor.apply();
                    }

                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finish();
                } else {
                    String message = "잠시 후 다시 시도해 주십시오";
                    Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
                    Log.v("success", "1");
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}
