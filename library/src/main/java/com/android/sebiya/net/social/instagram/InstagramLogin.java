package com.android.sebiya.net.social.instagram;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import com.android.sebiya.net.social.AuthCallbacks;

public class InstagramLogin {

    public static Builder with(FragmentManager fragmentManager) {
        return new Builder(fragmentManager);
    }

    public static class Builder {

        private final FragmentManager fragmentManager;

        String clientId;

        String redirectUrl;

        int minWidth;

        int minHeight;

        private AuthCallbacks<String> authCallback;

        private Builder(FragmentManager fragmentManager) {
            this.fragmentManager = fragmentManager;
        }

        public Builder withClientInfo(String clientId, String redirectUrl) {
            this.clientId = clientId;
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
            return InstagramAuthDialogFragment.show(fragmentManager, this);
        }
    }

}
