package udacity.example.com.bakingtime.ui;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import udacity.example.com.bakingtime.R;

public class RecipeStepSinglePageFragment extends Fragment implements ExoPlayer.EventListener {

    private static final String EXTRA_STEP_ID = "EXTRA_STEP_ID";
    private static final String EXTRA_DESCRIPTION_ID = "EXTRA_DESCRIPTION_ID";
    private static final String EXTRA_VIDEO_URL_ID = "EXTRA_VIDEO_URL_ID";
    private static final String EXTRA_IMAGE_URL_ID = "EXTRA_IMAGE_URL_ID";
    private static final String PLAY_WHEN_READY = "PLAY_WHEN_READY";
    private static final String CURRENT_WINDOW = "CURRENT_WINDOW";
    private static final String PLAYBACK_POSITION = "PLAYBACK_POSITION";
    private static final String EXTRA_STEPS_LIST_SIZE = "STEPS_LIST_SIZE";

    @BindView(R.id.playerView)
    SimpleExoPlayerView mPlayerView;

    @BindView(R.id.step_description_tv)
    TextView descriptionTextView;

    @BindView(R.id.step_image_view)
    ImageView imageView;

    @BindView(R.id.next)
    View next;

    @BindView(R.id.previous)
    View previous;

    private SimpleExoPlayer player;
    private MediaSessionCompat mediaSession;
    private PlaybackStateCompat.Builder stateBuilder;

    private boolean playWhenReady = true;
    private int currentWindow;
    private long playBackPosition;

    private Boolean mTwoPane;
    private String mStepId;
    private String mVideoUrl;
    private String mImageUrl;
    private String mDescription;
    private int mStepsListSize;
    private Unbinder mUnbinder;


    public RecipeStepSinglePageFragment() {
    }

    public static RecipeStepSinglePageFragment newInstance(String stepId, String videoUrl, String description, String imageUrl, int stepsListSize) {
        Bundle arguments = new Bundle();
        arguments.putString(EXTRA_STEP_ID, stepId);
        arguments.putString(EXTRA_DESCRIPTION_ID, description);
        arguments.putString(EXTRA_VIDEO_URL_ID, videoUrl);
        arguments.putString(EXTRA_IMAGE_URL_ID, imageUrl);
        arguments.putInt(EXTRA_STEPS_LIST_SIZE, stepsListSize);
        RecipeStepSinglePageFragment fragment = new RecipeStepSinglePageFragment();
        fragment.setArguments(arguments);
        return fragment;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(PLAY_WHEN_READY, playWhenReady);
        outState.putInt(CURRENT_WINDOW, currentWindow);
        outState.putLong(PLAYBACK_POSITION, playBackPosition);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTwoPane = getResources().getBoolean(R.bool.two_pane);

        if (getArguments() != null) {
            mStepId = getArguments().getString(EXTRA_STEP_ID);
            mDescription = getArguments().getString(EXTRA_DESCRIPTION_ID);
            mImageUrl = getArguments().getString(EXTRA_IMAGE_URL_ID);
            mVideoUrl = getArguments().getString(EXTRA_VIDEO_URL_ID);
            mStepsListSize = getArguments().getInt(EXTRA_STEPS_LIST_SIZE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            playWhenReady = savedInstanceState.getBoolean(PLAY_WHEN_READY);
            currentWindow = savedInstanceState.getInt(CURRENT_WINDOW);
            playBackPosition = savedInstanceState.getLong(PLAYBACK_POSITION);
        }

        View view = inflater.inflate(R.layout.master_recipe_action_fragment, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        descriptionTextView.setText(mDescription);
        initializeButtons();

    }

    private void initializeMediaSession() {
        mediaSession = new MediaSessionCompat(getContext(), "RecipeStepSinglePageFragment");

        mediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        mediaSession.setMediaButtonReceiver(null);
        stateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                        PlaybackStateCompat.ACTION_PAUSE |
                        PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                        PlaybackStateCompat.ACTION_PLAY_PAUSE);
        mediaSession.setPlaybackState(stateBuilder.build());
        mediaSession.setCallback(new MediaSessionCompat.Callback() {
            @Override
            public void onPlay() {
                player.setPlayWhenReady(true);
            }

            @Override
            public void onPause() {
                player.setPlayWhenReady(false);
            }

            @Override
            public void onSkipToPrevious() {
                player.seekTo(playBackPosition);
            }
        });
        mediaSession.setActive(true);
    }


    private void expandVideoView (SimpleExoPlayerView exoPlayer) {
        exoPlayer.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
        exoPlayer.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mVideoUrl != null && !mVideoUrl.isEmpty()) {
            initPrepareAndPlay();

        } else if (mImageUrl != null && !mImageUrl.isEmpty()){
            mPlayerView.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
            Picasso.with(getContext()).load(mImageUrl).fit().centerCrop()
                    .error(R.drawable.ic_local_dining_black_24dp)
                    .into(imageView);

        }else {
            //hide exoPlayer
            mPlayerView.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageResource(R.drawable.ic_local_dining_black_24dp);
        }
    }

    private void initPrepareAndPlay() {
        initializeMediaSession();
        initPlayer(Uri.parse(mVideoUrl));

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Expand video, hide mDescription, hide system UI
            if (!mTwoPane) {
                expandVideoView(mPlayerView);
                descriptionTextView.setVisibility(View.GONE);
                next.setVisibility(View.GONE);
                previous.setVisibility(View.GONE);
                imageView.setVisibility(View.GONE);
                hideSystemUI();
            }
        }
        playBackPosition = player.getCurrentPosition();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    public void initPlayer(Uri videoURL) {
        if (player == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
            mPlayerView.setPlayer(player);
            player.addListener(this);
            String userAgent = Util.getUserAgent(getContext(), "Baking Time");
            MediaSource mediaSource = new ExtractorMediaSource(videoURL,
                    new DefaultDataSourceFactory(getContext(), userAgent),
                    new DefaultExtractorsFactory(),
                    null,
                    null);
            player.prepare(mediaSource);
            player.setPlayWhenReady(playWhenReady);
            player.seekTo(playBackPosition);

        }
    }


    private void releasePlayer() {
        if (player != null) {
            playBackPosition = player.getCurrentPosition();
            player.stop();
            player.release();
            player = null;
        }

        if (mediaSession != null) {
            mediaSession.setActive(false);
        }
    }

    private void hideSystemUI() {
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if ((playbackState == ExoPlayer.STATE_READY) && playWhenReady) {
            stateBuilder.setState(PlaybackStateCompat.STATE_PLAYING, player.getCurrentPosition(), 1f);
        } else if (playbackState == ExoPlayer.STATE_READY) {
            stateBuilder.setState(PlaybackStateCompat.STATE_PAUSED, player.getCurrentPosition(), 1f);
        }
        mediaSession.setPlaybackState(stateBuilder.build());
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    private void initializeButtons() {
        if (mTwoPane) {
            next.setVisibility(View.GONE);
            previous.setVisibility(View.GONE);
        } else {
            int steps = mStepsListSize - 2;
            int id = Integer.parseInt(mStepId);
            if (id == steps) {
                next.setVisibility(View.INVISIBLE);
            }
            if (id == 0) {
                previous.setVisibility(View.INVISIBLE);
            }
        }
    }

}

