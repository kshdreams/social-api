package com.android.sebiya.net.social.instagram.response;

import android.support.annotation.Keep;
import com.google.gson.annotations.SerializedName;

@Keep
public class Meta {

    private int code;

    @SerializedName("error_message")
    private String errorMessage;

    @SerializedName("error_type")
    private String errorType;

    public int getCode() {
        return this.code;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("code - ");
        stringBuilder.append(this.code);
        stringBuilder.append(", error - ");
        stringBuilder.append(this.errorMessage);
        return stringBuilder.toString();
    }
}