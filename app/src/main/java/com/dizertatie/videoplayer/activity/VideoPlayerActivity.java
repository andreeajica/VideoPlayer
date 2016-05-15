package com.dizertatie.videoplayer.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dizertatie.videoplayer.R;
import com.dizertatie.videoplayer.objects.Video;
import com.dizertatie.videoplayer.utils.Constants;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

/**
 * Created by Andreea on 5/15/2016.
 */
public class VideoPlayerActivity extends YouTubeBaseActivity{


    private static String TAG = "VideoPlayerActivity";
    Toolbar toolbar;
    YouTubePlayerView videoView;
    YouTubePlayer.OnInitializedListener onInitializedListener;
    Video video;
    boolean isFullScreen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        if (getIntent() != null && getIntent().hasExtra("video")) {
            video = (Video)getIntent().getSerializableExtra("video");

        }
        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setupToolbar();
            initView(video);

            initVideo(video.id, savedInstanceState);
        }else if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){

            initVideo(video.id, savedInstanceState);

        }


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            isFullScreen = false;
        }else if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            isFullScreen = true;

        }
    }


    public void initView(Video video){


        TextView video_title = (TextView)findViewById(R.id.title);
        TextView video_desc = (TextView)findViewById(R.id.desc);

        video_title.setText(video.title);
        video_desc.setText(video.description);
    }

    public void initVideo(final String videoId, final Bundle savedInstanceState){
        videoView = (YouTubePlayerView) findViewById(R.id.video);

        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(videoId);
                youTubePlayer.setFullscreen(isFullScreen);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };

        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.initialize(Constants.browserKey, onInitializedListener);
            }
        });
        videoView.callOnClick();

    }

    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        ImageView back = (ImageView)findViewById(R.id.ic_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

}
