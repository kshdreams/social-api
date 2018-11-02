package com.android.sebiya.net.social.instagram.response.media;

import android.support.annotation.Keep;
import com.google.gson.annotations.SerializedName;

@Keep
public class Caption {

    @SerializedName("created_time")
    private String createdTime;

    @SerializedName("text")
    private String text;

    public String getText() {
        return this.text;
    }

    public String getCreatedTime() {
        return this.createdTime;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{caption - ");
        stringBuilder.append(this.text);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}