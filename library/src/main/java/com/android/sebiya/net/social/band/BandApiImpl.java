package com.android.sebiya.net.social.band;

import com.android.sebiya.net.SimpleRetrofit;
import com.android.sebiya.net.social.band.response.Response;
import com.android.sebiya.net.social.band.response.group.BandInfoResponse;
import com.android.sebiya.net.social.band.response.media.AlbumListResponse;
import com.android.sebiya.net.social.band.response.media.PhotoListResponse;
import com.android.sebiya.net.social.band.response.user.UserInfo;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import java.util.concurrent.Callable;

public class BandApiImpl implements BandApi {

    private BandApi mApi;

    public BandApi getApi() {
        if (mApi == null) {
            // TODO : make okhttp client if you want to add interceptors
            mApi = SimpleRetrofit.create(BandUrls.API_URL, BandApi.class);
        }
        return mApi;
    }

    @Override
    public Single<AlbumListResponse> albumList(final String token, final String bandKey, final String after) {
        return Single.defer(new Callable<SingleSource<? extends AlbumListResponse>>() {
            @Override
            public SingleSource<? extends AlbumListResponse> call() throws Exception {
                return getApi().albumList(token, bandKey, after);
            }
        });
    }

    @Override
    public Single<BandInfoResponse> bandList(final String token) {
        return Single.defer(new Callable<SingleSource<? extends BandInfoResponse>>() {
            @Override
            public SingleSource<? extends BandInfoResponse> call() throws Exception {
                return getApi().bandList(token);
            }
        });
    }

    @Override
    public Single<PhotoListResponse> photoList(final String token, final String bandKey, final String photoAlbumKey,
            final String after) {
        return Single.defer(new Callable<SingleSource<? extends PhotoListResponse>>() {
            @Override
            public SingleSource<? extends PhotoListResponse> call() throws Exception {
                return getApi().photoList(token, bandKey, photoAlbumKey, after);
            }
        });
    }

    @Override
    public Single<Response<UserInfo>> userInfo(final String token, final String bandKey) {
        return Single.defer(new Callable<SingleSource<? extends Response<UserInfo>>>() {
            @Override
            public SingleSource<? extends Response<UserInfo>> call() throws Exception {
                return getApi().userInfo(token, bandKey);
            }
        });
    }
}
