package com.android.sebiya.net.social.band.response.comment;

import android.support.annotation.Keep;
import com.android.sebiya.net.social.band.response.media.Owner;
import com.google.gson.annotations.SerializedName;

@Keep
public class Comment {
    @SerializedName("body")
    private String body;

    @SerializedName("author")
    private Owner author;

    @SerializedName("created_at")
    private long createdAt;

    public String getBody() {
        return body;
    }

    public Owner getAuthor() {
        return author;
    }

    public long getCreatedAt() {
        return createdAt;
    }
}
