package com.android.sebiya.net.social;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.android.sebiya.net.R;

public abstract class AuthDialogFragment<T> extends DialogFragment {

    private AuthCallbacks<T> mAuthCallbacks;
    private View mProgress;
    private WebView mWebView;

    protected abstract String getAuthUrl();
    protected abstract boolean shouldOverrideUrlLoading(WebView webView, String url, AuthCallbacks<T> callback);
    protected abstract T getAuthResult();

    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        return layoutInflater.inflate(R.layout.dialog_fragment_auth, viewGroup, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.mWebView = view.findViewById(R.id.webView);
        this.mProgress = view.findViewById(R.id.progress);
        if (getArguments() == null) {
            throw new IllegalArgumentException("this dialog should include argument clientId and redirect url");
        }
        getDialog().setCanceledOnTouchOutside(false);
        if (getActivity() instanceof AuthCallbacks) {
            this.mAuthCallbacks = (AuthCallbacks) getActivity();
        }
        if (this.mAuthCallbacks == null && (getParentFragment() instanceof AuthCallbacks)) {
            this.mAuthCallbacks = (AuthCallbacks) getParentFragment();
        }
        if (this.mAuthCallbacks == null) {
            throw new IllegalArgumentException("activity should implements auth callbacks");
        }

        CookieManager instance = CookieManager.getInstance();
        instance.removeAllCookies(new ValueCallback<Boolean>() {
            @Override
            public void onReceiveValue(final Boolean value) {

            }
        });
        instance.flush();
        this.mWebView.setHorizontalScrollBarEnabled(false);
        this.mWebView.getSettings().setTextZoom(100);
        this.mWebView.getSettings().setJavaScriptEnabled(true);
        this.mWebView.setBackgroundColor(-1);
        this.mWebView.setWebViewClient(new WebViewClient() {
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
        this.mWebView.loadUrl(getAuthUrl());
    }

    public void onDismiss(DialogInterface dialogInterface) {
        super.onDismiss(dialogInterface);
        if (getAuthResult() == null && mAuthCallbacks != null) {
            mAuthCallbacks.onAuthFailure();
        }
    }

}
