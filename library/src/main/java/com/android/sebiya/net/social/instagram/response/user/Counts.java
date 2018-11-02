package com.android.sebiya.net.social.instagram.response.user;

import android.support.annotation.Keep;
import com.google.gson.annotations.SerializedName;

@Keep
public class Counts {

    @SerializedName("followed_by")
    int follower;

    @SerializedName("follows")
    int following;

    @SerializedName("media")
    int media;

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Media {");
        stringBuilder.append(this.media);
        stringBuilder.append("}, Follower {");
        stringBuilder.append(this.follower);
        stringBuilder.append("}, Following {");
        stringBuilder.append(this.following);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}