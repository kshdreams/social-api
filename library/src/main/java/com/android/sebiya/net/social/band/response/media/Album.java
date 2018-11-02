package com.android.sebiya.net.social.band.response.media;

import android.support.annotation.Keep;
import com.google.gson.annotations.SerializedName;

@Keep
public class Album {

    @SerializedName("photo_album_key")
    String albumKey;

    @SerializedName("create_at")
    long createTimeMs;

    @SerializedName("name")
    String name;

    @SerializedName("owner")
    Owner owner;

    @SerializedName("photo_count")
    int photoCount;

    public String getName() {
        return this.name;
    }

    public String getAlbumKey() {
        return this.albumKey;
    }

    public int getPhotoCount() {
        return this.photoCount;
    }

    public long getCreateTimeMs() {
        return this.createTimeMs;
    }

    public Owner getOwner() {
        return this.owner;
    }
}