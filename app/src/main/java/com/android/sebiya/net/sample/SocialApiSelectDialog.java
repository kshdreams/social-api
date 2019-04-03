package com.android.sebiya.net.sample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.sebiya.net.social.band.BandLogin;
import com.android.sebiya.net.social.instagram.InstagramLogin;
import com.android.sebiya.simplearrayadapter.AbsArrayAdapter.AbsViewBinder;
import com.android.sebiya.simplearrayadapter.AbsArrayAdapter.OnItemClickListener;
import com.android.sebiya.simplearrayadapter.SimpleArrayAdapter;

public class SocialApiSelectDialog extends DialogFragment {
    private SimpleArrayAdapter<String> mSocialApiAdapter;
    public RecyclerView mRecyclerView;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_recycler_view, viewGroup, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        getDialog().setCanceledOnTouchOutside(false);

        mSocialApiAdapter = SimpleArrayAdapter.<String>with(getActivity())
                .setLayoutResId(R.layout.list_item_1_line_image)
                .addViewBinder(R.id.text1, new AbsViewBinder<String, TextView>() {
                    @Override
                    protected void bindView(final TextView textView, final String s) {
                        textView.setText(s);
                    }
                })
                .addViewBinder(R.id.thumbnail, new AbsViewBinder<String, ImageView>() {
                    @Override
                    protected void bindView(final ImageView imageView, final String s) {
                        imageView.setVisibility(View.GONE);
                    }
                })
                .withItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(final View view, final int i) {
                        String item = mSocialApiAdapter.getItem(i);
                        if ("Instagram".equals(item)) {
                            InstagramLogin.with(getFragmentManager())
                                    .withStartUrl("https://www.instagram.com/accounts/login/")
                                    .withFullScreenMode(true)
                                    .withClientInfo(InstagramClientInfo.CLIENT_ID,
                                            InstagramClientInfo.REDIRECT_URI)
//                                    .withMinWidth(getActivity().getWindowManager().getDefaultDisplay().getWidth())
//                                    .withMinHeight(
//                                            getActivity().getWindowManager().getDefaultDisplay().getHeight() - 200)
                                    .show();
                        } else if ("Band".equals(item)) {
                            BandLogin.with(getFragmentManager())
                                    .withClientInfo(BandClientInfo.CLIENT_ID,
                                            BandClientInfo.CLIENT_SECRET, BandClientInfo.REDIRECT_URL)
                                    .withMinHeight(1000)
                                    .show();
                        }
                        dismissAllowingStateLoss();
                    }
                })
                .build();
        this.mRecyclerView.setAdapter(this.mSocialApiAdapter);

        mSocialApiAdapter.addItem("Instagram");
        mSocialApiAdapter.addItem("Band");
    }
}