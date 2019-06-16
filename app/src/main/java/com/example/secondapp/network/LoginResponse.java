package com.example.secondapp.network;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("key")
    private String key;

    public LoginResponse(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
