package com.android.sebiya.net.social.band.response;

import android.support.annotation.Keep;
import com.google.gson.annotations.SerializedName;

@Keep
public class Paging {

    @SerializedName("previous_params")
    public Params prevParams;

    @SerializedName("next_params")
    public Params nextParams;

    public static class Params {

        @SerializedName("after")
        public String after;

        @SerializedName("band_key")
        public String bandKey;

        @SerializedName("limit")
        public int limit;

        @SerializedName("access_token")
        public String accessToken;
    }
}