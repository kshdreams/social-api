package com.android.sebiya.net.social.band;

import com.android.sebiya.net.social.band.response.auth.AuthResponse;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface BandAuthApi {

    @GET("/oauth2/token?grant_type=authorization_code")
    Single<AuthResponse> getAccessToken(@Query("code") String code, @Header("Authorization") String authHeader);

    @GET("/oauth2/token?grant_type=refresh_token")
    Single<AuthResponse> refreshToken(@Query("refresh_token") String refreshToken, @Header("Authorization") String authHeader);
}