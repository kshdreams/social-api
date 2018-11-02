package com.android.sebiya.net.social.instagram;

import com.android.sebiya.net.social.instagram.response.Response;
import com.android.sebiya.net.social.instagram.response.media.Comment;
import com.android.sebiya.net.social.instagram.response.media.UserMedia;
import com.android.sebiya.net.social.instagram.response.user.UserInfo;
import io.reactivex.Single;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface InstagramApi {
    @GET("/v1/media/{mediaId}/comments")
    Single<Response<List<Comment>>> mediaComments(@Path("mediaId") String mediaId, @Query("access_token") String token);

    @GET("/v1/users/self")
    Single<Response<UserInfo>> userSelf(@Query("access_token") String token);

    @GET("/v1/users/self/media/recent")
    Single<Response<List<UserMedia>>> userSelfRecentMedia(@Query("access_token") String token);

}