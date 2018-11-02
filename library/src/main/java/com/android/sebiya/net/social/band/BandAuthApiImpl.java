package com.android.sebiya.net.social.band;

import android.util.Base64;
import com.android.sebiya.net.social.band.response.auth.AuthResponse;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.Callable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class BandAuthApiImpl {

    private BandAuthApi mApi;

    public BandAuthApi getApi() {
        if (mApi == null) {
            // TODO : make okhttp client if you want to add interceptors
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BandUrls.AUTH_API_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mApi = retrofit.create(BandAuthApi.class);
        }
        return mApi;
    }

    public Single<AuthResponse> getAccessToken(final String code, final String clientId, final String clientSecret) {
        return Single.defer(new Callable<SingleSource<? extends AuthResponse>>() {
            @Override
            public SingleSource<? extends AuthResponse> call() throws Exception {
                return getApi().getAccessToken(code, makeAuthHeader(clientId, clientSecret));
            }
        });
    }

    public Single<AuthResponse> refreshToken(final String refreshToken, final String clientId, final String clientSecret) {
        return Single.defer(new Callable<SingleSource<? extends AuthResponse>>() {
            @Override
            public SingleSource<? extends AuthResponse> call() throws Exception {
                return getApi().refreshToken(refreshToken, makeAuthHeader(clientId, clientSecret));
            }
        });
    }

    private static String makeAuthHeader(String clientId, String clientSecret) {
        String authKey = clientId + ":" + clientSecret;
        StringBuilder header = new StringBuilder();
        header.append("Basic ");
        try {
            header.append(Base64.encodeToString(authKey.getBytes("UTF-8"), 2));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return header.toString();
    }

}
