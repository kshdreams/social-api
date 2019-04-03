package com.android.sebiya.net.social;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.android.sebiya.net.R;

public abstract class AuthDialogFragment<T> extends DialogFragment {

    protected static final String ARGS_WIDTH = "args_width";
    protected static final String ARGS_HEIGHT = "args_height";

    private AuthCallbacks<T> mAuthCallbacks;
    private View mProgress;

    protected abstract String getAuthUrl();
    protected abstract boolean shouldOverrideUrlLoading(WebView webView, String url, AuthCallbacks<T> callback);
    protected abstract T getAuthResult();

    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        return layoutInflater.inflate(R.layout.dialog_fragment_auth, viewGroup, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        final WebView webView = view.findViewById(R.id.webView);
        mProgress = view.findViewById(R.id.progress);
        if (getArguments() == null) {
            throw new IllegalArgumentException("this dialog should include argument clientId and redirect url");
        }
        getDialog().setCanceledOnTouchOutside(false);
        if (getActivity() instanceof AuthCallbacks) {
            mAuthCallbacks = (AuthCallbacks) getActivity();
        }
        if (mAuthCallbacks == null && (getParentFragment() instanceof AuthCallbacks)) {
            mAuthCallbacks = (AuthCallbacks) getParentFragment();
        }
        if (mAuthCallbacks == null) {
            throw new IllegalArgumentException("activity should implements auth callbacks");
        }

        int width = getArguments().getInt(ARGS_WIDTH);
        int height = getArguments().getInt(ARGS_HEIGHT);

        if (width > 0) {
            webView.setMinimumWidth(width);
            view.setMinimumWidth(width);
        }

        if (height > 0) {
            webView.setMinimumHeight(height);
            view.setMinimumHeight(height);
        }

        CookieManager instance = CookieManager.getInstance();
        instance.removeAllCookies(new ValueCallback<Boolean>() {
            @Override
            public void onReceiveValue(final Boolean value) {

            }
        });
        instance.flush();
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setTextZoom(100);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setBackgroundColor(-1);
        webView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView webView, String str) {
                super.onPageFinished(webView, str);
                mProgress.setVisibility(View.GONE);
            }

            public boolean shouldOverrideUrlLoading(WebView webView, String url) {
                if (AuthDialogFragment.this.shouldOverrideUrlLoading(webView, url, mAuthCallbacks)) {
                    return true;
                }
                return super.shouldOverrideUrlLoading(webView, url);
            }
        });
        webView.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(final View v, final int keyCode, final KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (webView.canGoBack()) {
                        webView.goBack();
                        return true;
                    }
                }
                return false;
            }
        });
        WebSettings settings = webView.getSettings();
        settings.setDomStorageEnabled(true);
        onStartLoad(webView);
    }

    protected void onStartLoad(WebView webView) {
        webView.loadUrl(getAuthUrl());
    }

    public void onDismiss(DialogInterface dialogInterface) {
        super.onDismiss(dialogInterface);
        if (getAuthResult() == null && mAuthCallbacks != null) {
            mAuthCallbacks.onAuthFailure();
        }

        CookieManager instance = CookieManager.getInstance();
        instance.removeAllCookies(new ValueCallback<Boolean>() {
            @Override
            public void onReceiveValue(final Boolean value) {

            }
        });
        instance.flush();
    }

}
