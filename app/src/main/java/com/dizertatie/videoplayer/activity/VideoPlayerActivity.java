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

//activitaea care reda videourile
//foloseste libraria YotTubeAndroidPlayerApi pentru playerul video
//ultimele api-uri de la google nu mai suporta redarea video pe player-ul nativ
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
        //primim prin intent obiectul video care contine datele referitoare la filmuletul selectat
        if (getIntent() != null && getIntent().hasExtra("video")) {
            video = (Video)getIntent().getSerializableExtra("video");

        }
        //verificam orientarea telefonului
        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setupToolbar();
            initView(video);

            initVideo(video.id, savedInstanceState);
        }else if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){

            initVideo(video.id, savedInstanceState);

        }


    }
    //se apeleaza in momentul in care activitatea este reincarcata
    //o folosim pentru a salva orientarea telefonului
    //la fiecare schimbare de orientare activitatea este incarcata din nou
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            isFullScreen = false;
        }else if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            isFullScreen = true;

        }
    }

    // se iau referintele pentru texte si se seteaza titlul si descrierea filmului
    public void initView(Video video){


        TextView video_title = (TextView)findViewById(R.id.title);
        TextView video_desc = (TextView)findViewById(R.id.desc);

        video_title.setText(video.title);
        video_desc.setText(video.description);
    }
    // initializarea playerului
    public void initVideo(final String videoId, final Bundle savedInstanceState){
        videoView = (YouTubePlayerView) findViewById(R.id.video);
// se apeleaza dupa ce playerul a fost initializat
        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                //reda continutul in functie de id-ul clipului obtinut de la server
                //playerul este fullscreen sau nu in functie de orientarea telefonului(prin booleana isFullScreen)
                youTubePlayer.loadVideo(videoId);
                youTubePlayer.setFullscreen(isFullScreen);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };
        //la apasarea pe zona destinata playerului acesta se initializeaza cu apiKey-ul din aplicatie
        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.initialize(Constants.browserKey, onInitializedListener);
            }
        });
        //simulam apasarea pe zona playerului pentru a porni automat clipul
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
