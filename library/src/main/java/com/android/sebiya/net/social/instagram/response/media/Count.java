package com.android.sebiya.net.social.instagram.response.media;

import android.support.annotation.Keep;
import com.google.gson.annotations.SerializedName;

@Keep
public class Count {

    @SerializedName("count")
    private int count;

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{count - ");
        stringBuilder.append(this.count);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}