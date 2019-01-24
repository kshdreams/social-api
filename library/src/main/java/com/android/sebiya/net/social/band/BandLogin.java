package com.android.sebiya.net.social.band;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import com.android.sebiya.net.social.AuthCallbacks;
import com.android.sebiya.net.social.band.response.auth.AuthResponse;

public class BandLogin {

    public static Builder with(FragmentManager fragmentManager) {
        return new Builder(fragmentManager);
    }

    public static class Builder {

        private final FragmentManager fragmentManager;

        String clientId;

        String clientSeceret;

        String redirectUrl;

        int minWidth;
        int minHeight;

        private AuthCallbacks<AuthResponse> authCallback;

        private Builder(FragmentManager fragmentManager) {
            this.fragmentManager = fragmentManager;
        }

        public Builder withClientInfo(String clientId, String clientSeceret, String redirectUrl) {
            this.clientId = clientId;
            this.clientSeceret = clientSeceret;
            this.redirectUrl = redirectUrl;
            return this;
        }

        public Builder withMinWidth(int width) {
            this.minWidth = width;
            return this;
        }

        public Builder withMinHeight(int height) {
            this.minHeight = height;
            return this;
        }

        public DialogFragment show() {
            return BandAuthDialogFragment.show(fragmentManager, this);
        }
    }

}
