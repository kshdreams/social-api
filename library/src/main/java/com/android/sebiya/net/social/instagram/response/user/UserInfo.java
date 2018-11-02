package com.android.sebiya.net.social.instagram.response.user;

import android.support.annotation.Keep;
import com.google.gson.annotations.SerializedName;

@Keep
public class UserInfo {

    @SerializedName("bio")
    private String bio;

    @SerializedName("counts")
    private Counts counts;

    @SerializedName("full_name")
    private String fullName;

    @SerializedName("id")
    private String id;

    @SerializedName("is_business")
    private String isBusiness;

    @SerializedName("profile_picture")
    private String profilePicture;

    @SerializedName("username")
    private String userName;

    @SerializedName("website")
    private String website;

    public String getId() {
        return this.id;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getFullName() {
        return this.fullName;
    }

    public String getBio() {
        return this.bio;
    }

    public Counts getCounts() {
        return this.counts;
    }

    public String getProfilePicture() {
        return this.profilePicture;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("name - ");
        stringBuilder.append(this.userName);
        stringBuilder.append(", fullName - ");
        stringBuilder.append(this.fullName);
        stringBuilder.append(", counts (");
        stringBuilder.append(this.counts.media);
        stringBuilder.append(", ");
        stringBuilder.append(this.counts.following);
        stringBuilder.append(", ");
        stringBuilder.append(this.counts.follower);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}