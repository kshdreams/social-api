package com.android.sebiya.net.social.instagram.response.media;

import android.support.annotation.Keep;
import com.android.sebiya.net.social.instagram.response.user.UserInfo;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@Keep
public class UserMedia {

    private Caption caption;

    @SerializedName("carousel_media")
    private List<UserMedia> carouselMedia;

    private Count comments;

    @SerializedName("created_time")
    private String createdTime;

    private String id;

    private Medias images;

    private Count likes;

    private String link;

    private List<String> tags;

    private String type;

    private UserInfo user;

    private Medias videos;

    public interface Type {

        public static final String CAROUSEL = "carousel";
        public static final String IMAGE = "image";
        public static final String VIDEO = "video";
    }

    public String getLink() {
        return this.link;
    }

    public long getCreatedTimeMs() {
        try {
            return Long.parseLong(getCreatedTime()) * 1000;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private String getCreatedTime() {
        return this.createdTime;
    }

    public String getType() {
        return this.type;
    }

    public UserInfo getUser() {
        return this.user;
    }

    public Caption getCaption() {
        return this.caption;
    }

    public Medias getImages() {
        return this.images;
    }

    public List<UserMedia> getCarouselMedia() {
        return this.carouselMedia;
    }

    public Medias getVideos() {
        return this.videos;
    }

    public String getThumbnails() {
        return this.images.getLow().getUrl();
    }

    public String getId() {
        return this.id;
    }

    public List<String> getTags() {
        return this.tags;
    }

    public boolean isVideo() {
        return Type.VIDEO.equalsIgnoreCase(this.type);
    }

    public boolean isImage() {
        return Type.IMAGE.equalsIgnoreCase(this.type);
    }

    public boolean isCarousel() {
        return Type.CAROUSEL.equalsIgnoreCase(this.type);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("contentId - ");
        stringBuilder.append(this.id);
        stringBuilder.append(", likes");
        stringBuilder.append(this.likes);
        stringBuilder.append(", comments");
        stringBuilder.append(this.comments);
        stringBuilder.append(", ");
        stringBuilder.append(this.caption);
        return stringBuilder.toString();
    }
}