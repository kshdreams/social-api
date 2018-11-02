package com.android.sebiya.net.social.band.response;

import android.support.annotation.Keep;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@Keep
public class PagingItem<T> {

    @SerializedName("items")
    private List<T> items;

    @SerializedName("paging")
    private Paging paging;

    public List<T> getItems() {
        return this.items;
    }

    public Paging getPaging() {
        return this.paging;
    }
}