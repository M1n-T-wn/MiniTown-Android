package com.example.minitown.login.ui.data;

import com.example.minitown.user.ProfileRequest;
import com.example.minitown.user.ProfileResponse;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserService {

    @POST("/api/auth/login")
    Call<TokenResponse> loginUser(@Body LoginRequest loginRequest);

    @POST("/api/auth/signup")
    Call<TokenResponse> registerUsers(@Body RegisterRequest registerRequest);

    @POST("/api/auth/phone")
    Call<Void> sendSMS(@Body SendSMSRequest sendSMSRequest);

    @POST("/api/auth/phone/{phoneNum}")
    Call<ReceiverResponse> receiver(@Path("phoneNum") ReceiverRequest receiverRequest, @Query("rand") ReceiveRandomRequest receiverRandomRequest);

    @PATCH("/api/mypage")
    Call<ProfileResponse> profile(@Body TokenResponse tokenResponse);

}
