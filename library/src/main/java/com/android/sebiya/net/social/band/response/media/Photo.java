package com.android.sebiya.net.social.band.response.media;

import android.support.annotation.Keep;
import com.google.gson.annotations.SerializedName;

@Keep
public class Photo {

    @SerializedName("photo_album_key")
    String albumKey;

    @SerializedName("author")
    Owner author;

    @SerializedName("comment_count")
    int commentCount;

    @SerializedName("created_at")
    long createdTime;

    @SerializedName("emotion_count")
    int emotionCount;

    @SerializedName("height")
    int height;

    @SerializedName("is_video_thumbnail")
    boolean isVideo;

    @SerializedName("photo_key")
    String photoKey;

    @SerializedName("url")
    String url;

    @SerializedName("width")
    int width;

    public String getDescription() {
        return null;
    }

    public String getUrl() {
        return this.url;
    }

    public boolean isVideo() {
        return this.isVideo;
    }

    public String getAlbumId() {
        return this.albumKey;
    }

    public long getTimestamp() {
        return this.createdTime;
    }

    public String getId() {
        return this.photoKey;
    }

    public Owner getAuthor() {
        return this.author;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}