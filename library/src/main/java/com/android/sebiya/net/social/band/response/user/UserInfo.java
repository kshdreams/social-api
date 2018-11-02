package com.android.sebiya.net.social.band.response.user;

import android.support.annotation.Keep;
import com.google.gson.annotations.SerializedName;

@Keep
public class UserInfo {
    @SerializedName("message_allowed")
    private boolean isAllowedMessage;
    @SerializedName("is_app_member")
    private boolean isAppMember;
    @SerializedName("profile_image_url")
    private String profileUrl;
    @SerializedName("user_key")
    private String userKey;
    @SerializedName("name")
    private String userName;

    public String getUserKey() {
        return this.userKey;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getProfileUrl() {
        return this.profileUrl;
    }
}