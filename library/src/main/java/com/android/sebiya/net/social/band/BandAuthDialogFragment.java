package com.android.sebiya.net.social.band;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.webkit.WebView;
import com.android.sebiya.net.social.AuthCallbacks;
import com.android.sebiya.net.social.AuthDialogFragment;
import com.android.sebiya.net.social.band.BandLogin.Builder;
import com.android.sebiya.net.social.band.response.auth.AuthResponse;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class BandAuthDialogFragment extends AuthDialogFragment<AuthResponse> {

    protected static final String EXTRA_CLIENT_ID = "extra_client_id";

    protected static final String EXTRA_CLIENT_SECRET = "extra_client_secret";

    protected static final String EXTRA_REDIRECT_URL = "redirect_url";

    private String mClientId;

    private String mRedirectUrl;

    private String mClientSecret;

    private AuthResponse mTokenInfo;

    public static BandAuthDialogFragment show(FragmentManager fragmentManager, Builder builder) {
        BandAuthDialogFragment newDialog = newDialog(builder.clientId, builder.clientSeceret, builder.redirectUrl);
        Bundle args = newDialog.getArguments();
        if (args == null) {
            args = new Bundle();
        }
        args.putInt(ARGS_WIDTH, builder.minWidth);
        args.putInt(ARGS_HEIGHT, builder.minHeight);
        newDialog.setArguments(args);
        newDialog.show(fragmentManager, "band_auth");
        return newDialog;
    }

    public static BandAuthDialogFragment show(FragmentManager fragmentManager, String clientId, String clientSecret,
            String redirectUrl) {
        BandAuthDialogFragment newDialog = newDialog(clientId, clientSecret, redirectUrl);
        newDialog.show(fragmentManager, "band_auth");
        return newDialog;
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() == null) {
            return;
        }
        mClientId = getArguments().getString(EXTRA_CLIENT_ID);
        mRedirectUrl = getArguments().getString(EXTRA_REDIRECT_URL);
        mClientSecret = getArguments().getString(EXTRA_CLIENT_SECRET);
    }

    public static BandAuthDialogFragment newDialog(String clientId, String clientSecret, String redirectUrl) {
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_CLIENT_ID, clientId);
        bundle.putString(EXTRA_CLIENT_SECRET, clientSecret);
        bundle.putString(EXTRA_REDIRECT_URL, redirectUrl);
        BandAuthDialogFragment instagramAuthDialog = new BandAuthDialogFragment();
        instagramAuthDialog.setArguments(bundle);
        return instagramAuthDialog;
    }

    @Override
    protected String getAuthUrl() {
        return String.format(BandUrls.AUTH_URL, mClientId, mRedirectUrl);
    }

    @Override
    protected boolean shouldOverrideUrlLoading(final WebView webView, final String url,
            final AuthCallbacks<AuthResponse> callback) {
        if (url != null) {
            Uri parse = Uri.parse(url);
            Uri parse2 = Uri.parse(mRedirectUrl);
            if (parse.getHost().equals(parse2.getHost()) && parse.getPath().equals(parse2.getPath())) {
                String authCode = parse.getQueryParameter("code");

                getAccessTokenObservable(authCode, mClientId, mClientSecret)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<AuthResponse>() {
                            @Override
                            public void accept(final AuthResponse authResponse) throws Exception {
                                if (authResponse != null) {
                                    if (authResponse.getAccessToken() != null) {
                                        mTokenInfo = authResponse;
                                        if (callback != null) {
                                            callback.onAuthCompleted(mTokenInfo);
                                        }
                                    }
                                }
                                dismissAllowingStateLoss();
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(final Throwable throwable) throws Exception {
                                dismissAllowingStateLoss();
                            }
                        });
                return true;
            }
        }
        return false;
    }

    protected Single<AuthResponse> getAccessTokenObservable(String code, String clientId, String clientSecret) {
        return new BandAuthApiImpl().getAccessToken(code, clientId, clientSecret);
    }

    @Override
    protected AuthResponse getAuthResult() {
        return mTokenInfo;
    }
}
