package com.example.finalproject;

import com.google.gson.annotations.SerializedName;

public class UploadResponse {
    @SerializedName("result")
    public Message message;
    @SerializedName("success")
    public boolean success;
    @SerializedName("url")
    public String url;
}
