package com.android.sebiya.net.social.band.response.group;

import android.support.annotation.Keep;
import com.android.sebiya.net.social.band.response.Response;
import com.android.sebiya.net.social.band.response.group.BandInfoResponse.BandInfoArray;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@Keep
public class BandInfoResponse extends Response<BandInfoArray> {

    public static class BandInfoArray {

        @SerializedName("bands")
        List<BandInfo> bands;

        public List<BandInfo> getBands() {
            return this.bands;
        }
    }
}