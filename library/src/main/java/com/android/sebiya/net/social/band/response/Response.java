package com.android.sebiya.net.social.band.response;

import android.support.annotation.Keep;
import com.google.gson.annotations.SerializedName;

@Keep
public class Response<T> {

    @SerializedName("result_code")
    private int resultCode;

    @SerializedName("result_data")
    private T resultData;

    public T getResultData() {
        return this.resultData;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("resultCode[");
        stringBuilder.append(this.resultCode);
        stringBuilder.append("], data[");
        stringBuilder.append(this.resultData);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}