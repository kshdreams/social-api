package com.android.sebiya.net.social.band.response.media;

import android.support.annotation.Keep;
import com.google.gson.annotations.SerializedName;

@Keep
public class Owner {

    @SerializedName("description")
    String description;

    @SerializedName("name")
    String name;

    @SerializedName("profile_image_url")
    String profileUrl;

    @SerializedName("role")
    String role;

    @SerializedName("user_key")
    String userKey;

    public String getName() {
        return this.name;
    }

    public String getUserKey() {
        return this.userKey;
    }

    public String getProfileUrl() {
        return this.profileUrl;
    }

    public String getRole() {
        return this.role;
    }

    public String getDescription() {
        return this.description;
    }
}