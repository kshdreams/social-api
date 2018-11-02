package com.android.sebiya.net.social.band;

import com.android.sebiya.net.social.band.response.Response;
import com.android.sebiya.net.social.band.response.group.BandInfoResponse;
import com.android.sebiya.net.social.band.response.media.AlbumListResponse;
import com.android.sebiya.net.social.band.response.media.PhotoListResponse;
import com.android.sebiya.net.social.band.response.user.UserInfo;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BandApi {

    @GET("/v2/band/albums")
    Single<AlbumListResponse> albumList(@Query("access_token") String token, @Query("band_key") String bandKey,
            @Query("after") String after);

    @GET("/v2.1/bands")
    Single<BandInfoResponse> bandList(@Query("access_token") String token);

    @GET("/v2/band/album/photos")
    Single<PhotoListResponse> photoList(@Query("access_token") String token, @Query("band_key") String bandKey,
            @Query("photo_album_key") String photoAlbumKey, @Query("after") String after);

    @GET("/v2/profile")
    Single<Response<UserInfo>> userInfo(@Query("access_token") String token, @Query("band_key") String bandKey);


}
