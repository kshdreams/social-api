package com.android.sebiya.net.social.instagram.response.media;

import android.support.annotation.Keep;
import com.google.gson.annotations.SerializedName;

@Keep
public class Medias {

    @SerializedName("low_resolution")
    private Media low;

    @SerializedName("standard_resolution")
    private Media standard;

    private Media thumbnail;

    public Media getLow() {
        return this.low;
    }

    public Media getStandard() {
        return this.standard;
    }

    public Media getThumbnail() {
        return this.thumbnail;
    }
}