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

import com.android.volley.toolbox.NetworkImageView;
import com.dizertatie.videoplayer.R;
import com.dizertatie.videoplayer.activity.VideoPlayerActivity;
import com.dizertatie.videoplayer.objects.Video;
import com.dizertatie.videoplayer.utils.VolleySingleton;

import java.util.ArrayList;

//clasa care se ocupa de setarea textelor si imaginilor corespunzatoare pentru fiecare element din lista
public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.ViewHolder> {

    public static String TAG = "VideosAdapter";

    Context context;
    ArrayList<Video>videos = new ArrayList<>();

    //constructorul clasei
    //este apelat in activitatea corespunzatoare si primeste de acolo contextul si lista de clipuri
    public VideosAdapter(Context context, ArrayList<Video> videos){
        this.context = context;
        this.videos.addAll(videos);
        Log.d(TAG, "videos: "+this.videos);
    }

    //selectarea layoutului pentru elemente
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.videos_item, parent, false);
        return new ViewHolder(v);

    }

    //se seteaza valorile pentru fiecare element de layout folosit (imagine, titlu, descriere)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Video video= videos.get(position);

        holder.itemView.setTag(video);
        holder.title.setText(video.title);
        holder.description.setText(video.description);
        holder.image.setDefaultImageResId(R.mipmap.no_video);
        holder.image.setErrorImageResId(R.mipmap.no_video);
        if(video.default_thumb!=null && !video.default_thumb.equals("")){
            holder.image.setImageUrl(video.default_thumb, VolleySingleton.getImageLoader(context));
        }
        //gestionarea click-ului pe un element din lista
        holder.item_wrapper.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //pornim activitatea VideoPlayerActivity prin intent
                //trimitem elementul de tip Video selectat
                Intent intent = new Intent(context, VideoPlayerActivity.class);
                intent.putExtra("video", video);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    //se ia referinta pe fiecare item din layout
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
