package com.android.sebiya.net.social.band.response.group;

import android.support.annotation.Keep;
import com.google.gson.annotations.SerializedName;

@Keep
public class BandInfo {

    @SerializedName("band_key")
    String bandKey;

    @SerializedName("cover")
    String coverUrl;

    @SerializedName("member_count")
    int memberCount;

    @SerializedName("name")
    String name;

    public String getName() {
        return this.name;
    }

    public String getBandKey() {
        return this.bandKey;
    }

    public String getCoverUrl() {
        return this.coverUrl;
    }

    public int getMemberCount() {
        return this.memberCount;
    }
}