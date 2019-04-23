package com.madfree.bakingapp.detail;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.madfree.bakingapp.R;
import com.madfree.bakingapp.data.Step;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class DetailInfoFragment extends Fragment {

    public static final String LOG_TAG = DetailInfoFragment.class.getSimpleName();

    private DetailViewModel sharedViewModel;
    private TextView mStepDescription;
    private PlayerView mPlayerView;
    private SimpleExoPlayer mPlayer;
    private ImageView mThumbnailView;
    private String mVideoUrl;
    private String mThumbnail;
    private long playerPosition = 0;
    private boolean mPlayWhenReady = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        Log.d(LOG_TAG, "Starting DetaiListFragement");

        sharedViewModel = ViewModelProviders.of(getActivity()).get(DetailViewModel.class);

        mThumbnailView = view.findViewById(R.id.thumbnail_image_view);
        mPlayerView = view.findViewById(R.id.player_view);
        mStepDescription = view.findViewById(R.id.step_description_txt);

        sharedViewModel.getStepInfo().observe(this, new Observer<Step>() {
            @Override
            public void onChanged(Step step) {
                Log.d(LOG_TAG, "Getting stepInfo of stepId: " + step.getStepId() + " from sharedViewModel");
                mThumbnail = step.getThumbnailURL();
                mVideoUrl = step.getVideoURL();
                Log.d(LOG_TAG, "Getting videoUrl of stepId: " + step.getVideoURL() + " from sharedViewModel");
                int orientation = getResources().getConfiguration().orientation;
                if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                    mStepDescription.setText(step.getDescription());
                }
                setupUI(true);
            }
        });
        return view;
    }

    private void setupUI(boolean hasVideoSource) {
        if (mVideoUrl != null && !mVideoUrl.equals("")) {
            initExoPlayer(Uri.parse(mVideoUrl), hasVideoSource);
            Log.d(LOG_TAG, "Starting ExoPlayer with: " + mVideoUrl);
        } else {
            mPlayerView.setVisibility(View.GONE);
            Log.d(LOG_TAG, "Can' start ExoPlayer with: " + mVideoUrl);
        }
        if ((mThumbnail != null) && !mThumbnail.equals("")) {
            mThumbnailView.setVisibility(View.VISIBLE);
            RequestOptions options = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .centerCrop()
                    .dontTransform()
                    .placeholder(R.mipmap.ic_launcher_round)
                    .error(R.mipmap.ic_launcher_round);
            Glide.with(getContext()).load(mThumbnail).apply(options)
                    .into(mThumbnailView);
        } else {
            mThumbnailView.setVisibility(View.GONE);
        }
    }

    private void initExoPlayer(Uri video, boolean hasVideoSource) {
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(), getString(R.string.app_name)), bandwidthMeter);
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(video);
        if (mPlayer == null) {
            mPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
            mPlayerView.setPlayer(mPlayer);
        } else {
            mPlayer.stop();
            if (hasVideoSource) {
                mPlayer.seekTo(0L);
                playerPosition = 0;
            }
        }
        mPlayer.prepare(videoSource);
        if (playerPosition != 0)
            mPlayer.seekTo(playerPosition);
        mPlayer.setPlayWhenReady(mPlayWhenReady);
        mPlayerView.setVisibility(View.VISIBLE);
    }
}
