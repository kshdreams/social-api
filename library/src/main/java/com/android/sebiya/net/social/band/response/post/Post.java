package com.android.sebiya.net.social.band.response.post;

import android.support.annotation.Keep;
import com.android.sebiya.net.social.band.response.media.Owner;
import com.android.sebiya.net.social.band.response.media.Photo;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@Keep
public class Post {

    @SerializedName("content")
    private String content;

    @SerializedName("author")
    private Owner author;

    @SerializedName("post_key")
    private String postKey;

    @SerializedName("comment_count")
    private int commentCount;

    @SerializedName("created_at")
    private long createdAt;

    @SerializedName("photos")
    private List<Photo> photos;

    @SerializedName("emotion_count")
    private int emotionCount;

    @SerializedName("band_key")
    private String bandKey;

    public String getContent() {
        return content;
    }

    public Owner getAuthor() {
        return author;
    }

    public String getPostKey() {
        return postKey;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public int getEmotionCount() {
        return emotionCount;
    }

    public String getBandKey() {
        return bandKey;
    }
}
