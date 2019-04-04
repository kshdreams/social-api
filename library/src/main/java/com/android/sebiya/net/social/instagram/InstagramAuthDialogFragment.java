package com.android.sebiya.net.social.instagram;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import com.android.sebiya.net.social.AuthCallbacks;
import com.android.sebiya.net.social.AuthDialogFragment;

public class InstagramAuthDialogFragment extends AuthDialogFragment<String> {

    private static final String LOG_TAG = "InstagramLogin";

    private static final String EXTRA_CLIENT_ID = "extra_client_id";

    private static final String EXTRA_REDIRECT_URL = "redirect_url";

    private static final String EXTRA_START_URL = "start_url";

    private static final String EXTRA_FULL_SCREEN_MODE = "full_screen_mode";

    private static final String EXTRA_FULL_SCREEN_STYLE_RES = "full_screen_style_res";

    private String mClientId;

    private String mRedirectUrl;

    private String mStartUrl;

    private String mToken;

    private boolean mFullScreenMode;

    public static InstagramAuthDialogFragment show(FragmentManager fragmentManager, InstagramLogin.Builder builder) {
        InstagramAuthDialogFragment newDialog = newDialog(builder.clientId, builder.redirectUrl);
        Bundle args = newDialog.getArguments();
        if (args == null) {
            args = new Bundle();
        }
        args.putInt(ARGS_WIDTH, builder.minWidth);
        args.putInt(ARGS_HEIGHT, builder.minHeight);
        args.putString(EXTRA_START_URL, builder.startUrl);
        args.putBoolean(EXTRA_FULL_SCREEN_MODE, builder.fullScreenMode);
        args.putInt(EXTRA_FULL_SCREEN_STYLE_RES, builder.fullScreenStyle);
        newDialog.setArguments(args);
        newDialog.show(fragmentManager, "auth");
        return newDialog;
    }

    public static InstagramAuthDialogFragment show(FragmentManager fragmentManager, String clientId,
            String redirectUrl) {
        InstagramAuthDialogFragment newDialog = newDialog(clientId, redirectUrl);
        newDialog.show(fragmentManager, "auth");
        return newDialog;
    }

    public static InstagramAuthDialogFragment newDialog(String clientId, String redirectUrl) {
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_CLIENT_ID, clientId);
        bundle.putString(EXTRA_REDIRECT_URL, redirectUrl);
        InstagramAuthDialogFragment instagramAuthDialog = new InstagramAuthDialogFragment();
        instagramAuthDialog.setArguments(bundle);
        return instagramAuthDialog;
    }


    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() == null) {
            return;
        }
        mClientId = getArguments().getString(EXTRA_CLIENT_ID);
        mRedirectUrl = getArguments().getString(EXTRA_REDIRECT_URL);
        mStartUrl = getArguments().getString(EXTRA_START_URL);
        mFullScreenMode = getArguments().getBoolean(EXTRA_FULL_SCREEN_MODE);
        if (mFullScreenMode) {
            @StyleRes int fullScreenStyle = getArguments().getInt(EXTRA_FULL_SCREEN_STYLE_RES);
            if (fullScreenStyle != -1) {
                setStyle(DialogFragment.STYLE_NORMAL, fullScreenStyle);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mFullScreenMode) {
            Window window = getDialog().getWindow();
            if (window == null) {
                return;
            }
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            window.setGravity(Gravity.CENTER);
            window.setAttributes(layoutParams);
        }
    }

    @Override
    protected String getAuthUrl() {
        return String.format(InstagramUrls.AUTH_URL, mClientId, mRedirectUrl);
    }

    @Override
    protected void onStartLoad(final WebView webView) {
        if (TextUtils.isEmpty(mStartUrl)) {
            super.onStartLoad(webView);
            return;
        }
        webView.loadUrl(mStartUrl);
    }

    @Override
    protected boolean shouldOverrideUrlLoading(final WebView webView, final String url,
            final AuthCallbacks<String> callback) {
        if (url == null) {
            return false;
        }
        if (url.startsWith("https://www.facebook.com")) {
            String newUrl = url.replace("www.facebook.com", "m.facebook.com");
            webView.loadUrl(newUrl);
            return true;
        }
        Uri uri = Uri.parse(url);
        if (uri.getPathSegments().isEmpty()) {
            Log.i(LOG_TAG, "shouldOverrideUrlLoading. instagram home page.");
            webView.loadUrl(getAuthUrl());
            return true;
        }
        if (!url.startsWith(mRedirectUrl)) {
            return false;
        }
        String replace = url.replace(mRedirectUrl + "#access_token=", "");
        mToken = replace;
        if (callback != null) {
            callback.onAuthCompleted(replace);
        }
        dismissAllowingStateLoss();
        return true;
    }

    @Override
    protected String getAuthResult() {
        return mToken;
    }
}
