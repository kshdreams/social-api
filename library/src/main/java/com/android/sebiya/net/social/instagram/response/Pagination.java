package com.android.sebiya.net.social.instagram.response;

import android.support.annotation.Keep;
import com.google.gson.annotations.SerializedName;

@Keep
public class Pagination {

    @SerializedName("next_max_id")
    private String nextMaxId;

    @SerializedName("next_url")
    private String nextUrl;
}