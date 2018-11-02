package com.android.sebiya.net.social.instagram.response;

import android.support.annotation.Keep;

@Keep
public class Response<T> {

    private T data;

    private Meta meta;

    private Pagination pagination;

    public T getData() {
        return this.data;
    }

    public Meta getMeta() {
        return this.meta;
    }

    public Pagination getPagination() {
        return this.pagination;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("meta[");
        stringBuilder.append(this.meta);
        stringBuilder.append("], data[");
        stringBuilder.append(this.data);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}