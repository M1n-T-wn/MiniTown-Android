package com.example.minitown.login.ui.data;

import com.google.gson.annotations.SerializedName;

public class SendSMSRequest {
    @SerializedName("phone")
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
