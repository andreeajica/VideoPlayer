package com.dizertatie.videoplayer.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dizertatie.videoplayer.R;
import com.dizertatie.videoplayer.objects.Channels;
import com.dizertatie.videoplayer.utils.Constants;
import com.dizertatie.videoplayer.utils.NetworkUtils;
import com.dizertatie.videoplayer.utils.OnCompleteListener;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Button search_btn;
    Button search_id_btn;
    TextView latest_search_btn;
    TextView latest_search;
    LinearLayout latest_search_wrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = MainActivity.this.getSharedPreferences(Constants.SHARED_PREFS, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        final TextInputEditText channel_name = (TextInputEditText)findViewById(R.id.channel_name);
        final TextInputEditText channel_id = (TextInputEditText)findViewById(R.id.channel_id);
        search_btn = (Button)findViewById(R.id.btn_search);
        search_id_btn = (Button)findViewById(R.id.btn_search_id);
        latest_search = (TextView)findViewById(R.id.text_latest_search);
        latest_search_btn = (TextView)findViewById(R.id.btn_latest_search);
        latest_search_wrapper = (LinearLayout)findViewById(R.id.latest_search_wrapper);



        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetworkUtils.showProgressDialog(MainActivity.this);
                search(channel_name.getText().toString(), "name");
            }
        });
        search_id_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetworkUtils.showProgressDialog(MainActivity.this);
                search(channel_id.getText().toString(), "id");
            }
        });
        latest_search_wrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetworkUtils.showProgressDialog(MainActivity.this);
                search(sharedPreferences.getString(Constants.CHANNEL_NAME,""), "name");
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        if(sharedPreferences.getString(Constants.CHANNEL_NAME,"").equals("")){
            latest_search_wrapper.setVisibility(View.GONE);
        }else{
            latest_search_wrapper.setVisibility(View.VISIBLE);
            latest_search_btn.setText(sharedPreferences.getString(Constants.CHANNEL_NAME,""));
        }
    }

    public void search(final String channel_name, final String type){
        try {
            NetworkUtils.getNetworkUtils(MainActivity.this).getChannelsList(channel_name, type,  new OnCompleteListener() {
                @Override
                public void onComplete(boolean status, Object data) {
                    if(status) {
                        NetworkUtils.hideProgressDialog();
                        Log.d("MainActivity", "complete");
                        Channels channels = (Channels)data;
                        Intent intent = new Intent(MainActivity.this, ChannelsActivity.class);
                        intent.putExtra("channels",channels);
                        if(type.equals("name")) {
                            editor.putString(Constants.CHANNEL_NAME, channel_name).apply();
                        }
                        startActivity(intent);


                    }else{
                        NetworkUtils.hideProgressDialog();
                        Toast.makeText(MainActivity.this, R.string.general_error, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
