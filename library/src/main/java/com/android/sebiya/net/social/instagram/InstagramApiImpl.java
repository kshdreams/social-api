package com.android.sebiya.net.social.instagram;

import com.android.sebiya.net.SimpleRetrofit;
import com.android.sebiya.net.social.instagram.response.Response;
import com.android.sebiya.net.social.instagram.response.media.Comment;
import com.android.sebiya.net.social.instagram.response.media.UserMedia;
import com.android.sebiya.net.social.instagram.response.user.UserInfo;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import java.util.List;
import java.util.concurrent.Callable;

public class InstagramApiImpl implements InstagramApi {

    private InstagramApi mApi;

    public InstagramApi getApi() {
        if (mApi == null) {
            // TODO : make okhttp client if you want to add interceptors
            mApi = SimpleRetrofit.create(InstagramUrls.API_URL, InstagramApi.class);
        }
        return mApi;
    }

    @Override
    public Single<Response<List<Comment>>> mediaComments(final String mediaId, final String token) {
        return Single.defer(new Callable<SingleSource<? extends Response<List<Comment>>>>() {
            @Override
            public SingleSource<? extends Response<List<Comment>>> call() throws Exception {
                return getApi().mediaComments(mediaId, token);
            }
        });
    }

    @Override
    public Single<Response<UserInfo>> userSelf(final String token) {
        return Single.defer(new Callable<SingleSource<? extends Response<UserInfo>>>() {
            @Override
            public SingleSource<? extends Response<UserInfo>> call() throws Exception {
                return getApi().userSelf(token);
            }
        });
    }

    @Override
    public Single<Response<List<UserMedia>>> userSelfRecentMedia(final String token) {
        return Single.defer(new Callable<SingleSource<? extends Response<List<UserMedia>>>>() {
            @Override
            public SingleSource<? extends Response<List<UserMedia>>> call() throws Exception {
                return getApi().userSelfRecentMedia(token);
            }
        });
    }

    @Override
    public Single<Response<List<UserMedia>>> userSelfRecentMediaNext(final String url) {
        return Single.defer(new Callable<SingleSource<? extends Response<List<UserMedia>>>>() {
            @Override
            public SingleSource<? extends Response<List<UserMedia>>> call() {
                return getApi().userSelfRecentMediaNext(url);
            }
        });
    }
}
