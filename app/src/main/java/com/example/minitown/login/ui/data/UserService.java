package com.example.minitown.login.ui.data;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {

    @POST("/api/auth/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @POST("/api/auth/signup")
    Call<RegisterResponse> registerUsers(@Body RegisterRequest registerRequest);
}
