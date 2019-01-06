package com.android.sebiya.net.sample;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.sebiya.net.social.AuthCallbacks;
import com.android.sebiya.net.social.band.BandApi;
import com.android.sebiya.net.social.band.BandApiImpl;
import com.android.sebiya.net.social.band.response.auth.AuthResponse;
import com.android.sebiya.net.social.band.response.group.BandInfo;
import com.android.sebiya.net.social.band.response.group.BandInfoResponse;
import com.android.sebiya.net.social.instagram.InstagramApi;
import com.android.sebiya.net.social.instagram.InstagramApiImpl;
import com.android.sebiya.net.social.instagram.response.Pagination;
import com.android.sebiya.net.social.instagram.response.Response;
import com.android.sebiya.net.social.instagram.response.media.UserMedia;
import com.android.sebiya.net.social.instagram.response.user.UserInfo;
import com.android.sebiya.simplearrayadapter.AbsArrayAdapter.AbsViewBinder;
import com.android.sebiya.simplearrayadapter.AbsArrayAdapter.HeaderViewListener;
import com.android.sebiya.simplearrayadapter.SimpleArrayAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AuthCallbacks<Object> {
    private static final String LOG_TAG = "MainActivity";

    private static final int TYPE_LOAD_MORE = -10;

    private static class Item {

        String text;

        String imageUrl;

        static Item create(String text, String image) {
            Item item = new Item();
            item.text = text;
            item.imageUrl = image;
            return item;
        }
    }

    private RecyclerView mRecyclerView;

    private TextView mMessage;

    private SimpleArrayAdapter<Item> mAdapter;

    private ImageView mProfileImage;

    private TextView mUserName;

    private View mInfoLayout;

    private InstagramApi mInstagramApi = new InstagramApiImpl();

    private Pagination mInstagramNextPage;

    private BandApi mBandApi = new BandApiImpl();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mInfoLayout = findViewById(R.id.info_layout);
        mMessage = findViewById(R.id.message);
        mRecyclerView = findViewById(R.id.recyclerView);
        mUserName = findViewById(R.id.userName);
        mProfileImage = findViewById(R.id.profileImage);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = SimpleArrayAdapter.<Item>with(this)
                .setLayoutResId(R.layout.list_item_1_line_image)
                .addViewBinder(R.id.text1, new AbsViewBinder<Item, TextView>() {
                    @Override
                    protected void bindView(final TextView textView, final Item item) {
                        textView.setText(item.text);
                    }
                })
                .addViewBinder(R.id.thumbnail, new AbsViewBinder<Item, ImageView>() {
                    @Override
                    protected void bindView(final ImageView imageView, final Item item) {
                        Glide.with(imageView).load(item.imageUrl).into(imageView);
                    }
                })
                .build();

        mRecyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SocialApiSelectDialog().show(getSupportFragmentManager(), "dialog");
            }
        });
    }


    @SuppressLint("CheckResult")
    @Override
    public void onAuthCompleted(final Object authResult) {
        Snackbar.make(mRecyclerView, "Auth completed. result - " + authResult, Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();

        mInfoLayout.setVisibility(View.VISIBLE);

        mInstagramNextPage = null;

        if (authResult instanceof String) {
            mMessage.setText("Login with Instagram.");
            String instagramToken = (String) authResult;
            mInstagramApi.userSelfRecentMedia(instagramToken)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            new Consumer<Response<List<UserMedia>>>() {
                                @Override
                                public void accept(final Response<List<UserMedia>> listResponse) {
                                    List<Item> data = new ArrayList<>();
                                    for (UserMedia media : listResponse.getData()) {
                                        data.add(Item.create(TextUtils.join(", ", media.getTags()),
                                                media.getThumbnails()));
                                    }
                                    mInstagramNextPage = listResponse.getPagination();
                                    mAdapter.swapArray(data);
                                    handleInstagramLoadMore();
                                }
                            }, new Consumer<Throwable>() {
                                @Override
                                public void accept(final Throwable throwable) throws Exception {
                                    Snackbar.make(mRecyclerView, "get media failure", Snackbar.LENGTH_SHORT)
                                            .setAction("Action", null).show();
                                }
                            });
            mInstagramApi.userSelf(instagramToken)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Response<UserInfo>>() {
                        @Override
                        public void accept(final Response<UserInfo> userInfoResponse) throws Exception {
                            showProfile(userInfoResponse.getData().getUserName(),
                                    userInfoResponse.getData().getProfilePicture());
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(final Throwable throwable) throws Exception {

                        }
                    });
        } else if (authResult instanceof AuthResponse) {
            mMessage.setText("Login with Band.");
            final AuthResponse bandAuthToken = (AuthResponse) authResult;
            mBandApi.bandList(bandAuthToken.getAccessToken())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<BandInfoResponse>() {
                        @Override
                        public void accept(final BandInfoResponse bandInfoResponse) throws Exception {
                            List<Item> data = new ArrayList<>();
                            for (BandInfo band : bandInfoResponse.getResultData().getBands()) {
                                data.add(Item.create(band.getName(), band.getCoverUrl()));
                            }
                            mAdapter.swapArray(data);
                            if (!bandInfoResponse.getResultData().getBands().isEmpty()) {
                                mBandApi.userInfo(bandAuthToken.getAccessToken(),
                                        bandInfoResponse.getResultData().getBands().get(0).getBandKey())
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(
                                                new Consumer<com.android.sebiya.net.social.band.response.Response<com.android.sebiya.net.social.band.response.user.UserInfo>>() {
                                                    @Override
                                                    public void accept(
                                                            final com.android.sebiya.net.social.band.response.Response<com.android.sebiya.net.social.band.response.user.UserInfo> userInfoResponse)
                                                            throws Exception {
                                                        showProfile(userInfoResponse.getResultData().getUserName(),
                                                                userInfoResponse.getResultData().getProfileUrl());
                                                    }
                                                }, new Consumer<Throwable>() {
                                                    @Override
                                                    public void accept(final Throwable throwable) throws Exception {

                                                    }
                                                });
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(final Throwable throwable) throws Exception {
                            Snackbar.make(mRecyclerView, "get band list failure", Snackbar.LENGTH_SHORT)
                                    .setAction("Action", null).show();
                        }
                    });
        }
    }

    private void handleInstagramLoadMore() {
        Log.i(LOG_TAG, "handleInstagramLoadMore. load more - " + mInstagramNextPage);
        if (mInstagramNextPage != null && mInstagramNextPage.getNextUrl() != null) {
            // TODO : how to remove header view?
            mAdapter.addHeaderView(mAdapter.getItemCount(), TYPE_LOAD_MORE, R.layout.list_item_load_more,
                    new HeaderViewListener() {
                        @Override
                        public void onCreateHeaderView(final View view, final int i) {
                            Log.i(LOG_TAG, "onCreateHeaderView");
                        }

                        @SuppressLint("CheckResult")
                        @Override
                        public void onBindHeaderView(final View view, final int i) {
                            Log.i(LOG_TAG, "onBindHeaderView");
                            View progress = view.findViewById(R.id.progress);
                            View loadMore = view.findViewById(R.id.load_more_button);
                            if (progress.getVisibility() != View.VISIBLE) {
                                loadMore.setVisibility(View.GONE);
                                progress.setVisibility(View.VISIBLE);
                                mInstagramApi.userSelfRecentMediaNext(mInstagramNextPage.getNextUrl())
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Consumer<Response<List<UserMedia>>>() {
                                            @Override
                                            public void accept(final Response<List<UserMedia>> listResponse) {
                                                View progress = view.findViewById(R.id.progress);
                                                View loadMore = view.findViewById(R.id.load_more_button);
                                                loadMore.setVisibility(View.VISIBLE);
                                                progress.setVisibility(View.GONE);

                                                mAdapter.removeHeaderViewByType(TYPE_LOAD_MORE);
                                                List<Item> data = new ArrayList<>();
                                                for (UserMedia media : listResponse.getData()) {
                                                    data.add(Item.create(TextUtils.join(", ", media.getTags()),
                                                            media.getThumbnails()));
                                                }
                                                mAdapter.addAll(data);
                                                mInstagramNextPage = listResponse.getPagination();
                                                Log.i(LOG_TAG, "onBindHeaderView. next page - " + mInstagramNextPage);
                                                handleInstagramLoadMore();
                                            }
                                        }, new Consumer<Throwable>() {
                                            @Override
                                            public void accept(final Throwable throwable) {

                                            }
                                        });
                            }
                        }
                    });
        } else {
            mAdapter.removeHeaderViewByType(TYPE_LOAD_MORE);
        }
    }

    @MainThread
    private void showProfile(String text, String image) {
        mUserName.setText(text);
        Glide.with(mProfileImage).load(image).apply(
                RequestOptions.circleCropTransform()).into(mProfileImage);
    }

    @Override
    public void onAuthFailure() {
        Snackbar.make(mRecyclerView, "Auth failure", Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
    }
}
