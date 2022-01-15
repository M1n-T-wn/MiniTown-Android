package com.example.minitown.user;

import com.example.minitown.login.ui.data.TokenResponse;

public class ProfileResponse extends TokenResponse {
    private String birth;
    private String info;
    private String name;

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getName(ProfileResponse profileResponse) {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
