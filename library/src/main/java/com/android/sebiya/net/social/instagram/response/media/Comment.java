package com.android.sebiya.net.social.instagram.response.media;

import android.support.annotation.Keep;
import com.android.sebiya.net.social.instagram.response.user.UserInfo;
import com.google.gson.annotations.SerializedName;

@Keep
public class Comment {

    @SerializedName("created_time")
    private String createdTime;

    private UserInfo from;

    private String id;

    private String text;

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("text - ");
        stringBuilder.append(this.text);
        return stringBuilder.toString();
    }
}