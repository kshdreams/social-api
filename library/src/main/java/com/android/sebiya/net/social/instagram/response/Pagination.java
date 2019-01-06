package com.android.sebiya.net.social.instagram.response;

import android.support.annotation.Keep;
import com.google.gson.annotations.SerializedName;

@Keep
public class Pagination {

    @SerializedName("next_max_id")
    private String nextMaxId;

    @SerializedName("next_url")
    private String nextUrl;

    public String getNextMaxId() {
        return nextMaxId;
    }

    public String getNextUrl() {
        return nextUrl;
    }

    @Override
    public String toString() {
        return "Pagination[nex - " + nextMaxId + ", url - " + nextUrl + "]";
    }
}