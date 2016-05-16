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
import com.dizertatie.videoplayer.adapters.ChannelsAdapter;
import com.dizertatie.videoplayer.objects.Channel;
import com.dizertatie.videoplayer.objects.Channels;

import java.util.ArrayList;
//activitatea in care se afiseaza informatiile despre canalul gasit
public class ChannelsActivity extends AppCompatActivity {

    public static String TAG = "ChannelsActivity";


    ArrayList<Channel>channels;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channels);
        Channels channelsObj = new Channels();
        channels = new ArrayList<>();
        //se primesc datele despre canal prin intent
        channelsObj = (Channels)getIntent().getSerializableExtra("channels");
        channels.addAll(channelsObj.channels);
        Log.d(TAG,"channels obj:"+channelsObj );
        Log.d(TAG,"channels:"+channels );

        initialiseUI();
    }
    //functia care se ocupa de generarea ecranului
    private void initialiseUI() {
        //alocarea functionalitatilor pe toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        //initializarea listei si setarea adapterului corespunzator
        //adapterul se ocupa de gestionarea informatiilor pentru fiecare element din lista
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.list);
        ChannelsAdapter adapter = new ChannelsAdapter(ChannelsActivity.this, channels);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(ChannelsActivity.this,1));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }
//actiunea la apasarea sagetii de pe toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);

    }
//functia care inchide activitatea cand se apasa back(sageata din toolbar sau butonul telefonului)
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

}
