package com.android.sebiya.net.social.band.response.auth;

import android.support.annotation.Keep;
import com.google.gson.annotations.SerializedName;

@Keep
public class AuthResponse {

    @SerializedName("access_token")
    String accessToken;

    @SerializedName("expires_in")
    long expiresIn;

    @SerializedName("refresh_token")
    String refreshToken;

    String scope;

    @SerializedName("token_type")
    String tokenType;

    @SerializedName("user_key")
    String userKey;

    public String getTokenType() {
        return this.tokenType;
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public String getRefreshToken() {
        return this.refreshToken;
    }

    public long getExpiresIn() {
        return this.expiresIn;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("token - ");
        stringBuilder.append(this.accessToken);
        return stringBuilder.toString();
    }
}