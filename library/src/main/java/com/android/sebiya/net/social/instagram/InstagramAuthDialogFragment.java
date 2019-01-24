package com.android.sebiya.net.social.instagram;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.webkit.WebView;
import com.android.sebiya.net.social.AuthCallbacks;
import com.android.sebiya.net.social.AuthDialogFragment;

public class InstagramAuthDialogFragment extends AuthDialogFragment<String> {

    private static final String EXTRA_CLIENT_ID = "extra_client_id";

    private static final String EXTRA_REDIRECT_URL = "redirect_url";

    private String mClientId;

    private String mRedirectUrl;

    private String mToken;

    public static InstagramAuthDialogFragment show(FragmentManager fragmentManager, InstagramLogin.Builder builder) {
        InstagramAuthDialogFragment newDialog = newDialog(builder.clientId, builder.redirectUrl);
        Bundle args = newDialog.getArguments();
        if (args == null) {
            args = new Bundle();
        }
        args.putInt(ARGS_WIDTH, builder.minWidth);
        args.putInt(ARGS_HEIGHT, builder.minHeight);
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

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() == null) {
            return;
        }
        mClientId = getArguments().getString(EXTRA_CLIENT_ID);
        mRedirectUrl = getArguments().getString(EXTRA_REDIRECT_URL);
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
    protected String getAuthUrl() {
        return String.format(InstagramUrls.AUTH_URL, mClientId, mRedirectUrl);
    }

    @Override
    protected boolean shouldOverrideUrlLoading(final WebView webView, final String url,
            final AuthCallbacks<String> callback) {
        if (url == null || !url.startsWith(mRedirectUrl)) {
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
