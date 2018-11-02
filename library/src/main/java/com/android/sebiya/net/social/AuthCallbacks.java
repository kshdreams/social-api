package com.android.sebiya.net.social;

public interface AuthCallbacks<T> {
    void onAuthCompleted(T authResult);

    void onAuthFailure();
}