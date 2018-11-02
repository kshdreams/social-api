package com.android.sebiya.net.social.instagram.response.media;

import android.support.annotation.Keep;
import com.google.gson.annotations.SerializedName;

@Keep
public class Media {

    @SerializedName("height")
    private int height;

    @SerializedName("width")
    private int width;

    @SerializedName("url")
    private String url;


    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public String getUrl() {
        return this.url;
    }
}