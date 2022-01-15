package com.example.minitown.login.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.minitown.R;
import com.example.minitown.login.ui.data.ApiClient;
import com.example.minitown.login.ui.data.SendSMSRequest;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendSmsActivity extends AppCompatActivity {
    private EditText et_sendSMS;
    private Button btn_sendSMS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms);

        et_sendSMS = (EditText) findViewById(R.id.et_sendSMS);
        btn_sendSMS = (Button) findViewById(R.id.btn_send);

        btn_sendSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendSMSRequest sendSMSRequest = new SendSMSRequest();
                String send = et_sendSMS.getText().toString();
                sendSMSRequest.setPhone(send);
                Toast.makeText(getApplicationContext(), send, Toast.LENGTH_SHORT).show();

                ApiClient.getService().sendSMS(sendSMSRequest).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                            Log.d("DebugLog", "Success:" + response.code());
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                            Log.d("DebugLog", "Fail");
                    }
                });
                Intent intent = new Intent(SendSmsActivity.this, RegisterActivity.class);
                intent.setAction("sendAction");
                intent.putExtra("phone", send);
                startActivity(intent);
            }
        });
    }
}