package com.dizertatie.videoplayer.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.dizertatie.videoplayer.R;
import com.dizertatie.videoplayer.activity.VideosActivity;
import com.dizertatie.videoplayer.objects.Channel;
import com.dizertatie.videoplayer.objects.Videos;
import com.dizertatie.videoplayer.utils.NetworkUtils;
import com.dizertatie.videoplayer.utils.OnCompleteListener;
import com.dizertatie.videoplayer.utils.VolleySingleton;

import java.util.ArrayList;

/**
 * Created by Andreea on 5/14/2016.
 */
public class ChannelsAdapter extends RecyclerView.Adapter<ChannelsAdapter.ViewHolder> {

    public static String TAG = "ChannelsAdapter";

    Context context;
    ArrayList<Channel>channels = new ArrayList<>();


    public ChannelsAdapter(Context context, ArrayList<Channel> channels){
        this.context = context;
        this.channels.addAll(channels);
        Log.d(TAG, "channels: "+this.channels);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.channels_item, parent, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Channel channel = channels.get(position);

        holder.itemView.setTag(channel);
        holder.title.setText(channel.title);
        holder.description.setText(channel.description);
        holder.image.setDefaultImageResId(R.mipmap.no_video);
        holder.image.setErrorImageResId(R.mipmap.no_video);
        if(channel.default_thumb!=null && !channel.default_thumb.equals("")){
            holder.image.setImageUrl(channel.default_thumb, VolleySingleton.getImageLoader(context));
        }

        holder.item_wrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetworkUtils.showProgressDialog(context);

                NetworkUtils.getNetworkUtils(context).getUploadedVideos(channel.uploads_id, new OnCompleteListener() {
                    @Override
                    public void onComplete(boolean status, Object data) {
                        if(status) {
                            NetworkUtils.hideProgressDialog();
                            Videos videos  = (Videos)data;
                            Intent intent = new Intent(context, VideosActivity.class);
                            intent.putExtra("videos", videos);
                            context.startActivity(intent);


                        }else{
                            NetworkUtils.hideProgressDialog();
                            Toast.makeText(context, context.getResources().getString(R.string.general_error), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return channels.size();
    }

    public void add(Channel item, int position) {
        channels.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(Channel item) {
        int position = channels.indexOf(item);
        channels.remove(position);
        notifyItemRemoved(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public NetworkImageView image;
        public TextView title;
        public TextView description;
        public LinearLayout item_wrapper;

        public ViewHolder(View v){
            super(v);
            image = (NetworkImageView)v.findViewById(R.id.image);
            title = (TextView)v.findViewById(R.id.title);
            description = (TextView)v.findViewById(R.id.desc);
            item_wrapper = (LinearLayout)v.findViewById(R.id.item_wrapper);

        }

    }
}
