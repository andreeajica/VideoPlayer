package com.dizertatie.videoplayer.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.dizertatie.videoplayer.R;
import com.dizertatie.videoplayer.adapters.VideosAdapter;
import com.dizertatie.videoplayer.objects.Video;
import com.dizertatie.videoplayer.objects.Videos;

import java.util.ArrayList;

/**
 * Created by Andreea on 5/14/2016.
 */
public class VideosActivity extends AppCompatActivity {

    public static String TAG = "VideosActivity";


    ArrayList<Video> videos;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channels);
        videos = new ArrayList<>();
        Videos videosObj = new Videos();
        videosObj = (Videos)getIntent().getSerializableExtra("videos");
        videos.addAll(videosObj.videos);
        Log.d(TAG,"videos obj:"+videosObj );
        Log.d(TAG,"videos:"+videos );

        initialiseUI();
    }

    private void initialiseUI() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.list);
        VideosAdapter adapter = new VideosAdapter(VideosActivity.this, videos);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(VideosActivity.this,1));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
