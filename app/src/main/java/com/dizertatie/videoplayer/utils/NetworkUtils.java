package com.dizertatie.videoplayer.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dizertatie.videoplayer.R;
import com.dizertatie.videoplayer.objects.Channel;
import com.dizertatie.videoplayer.objects.Channels;
import com.dizertatie.videoplayer.objects.Video;
import com.dizertatie.videoplayer.objects.Videos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

//clasa care se ocupa de requesturile catre serverele google
public class NetworkUtils {

    public static final String TAG = "NetworkUtils";
    private RequestQueue requestQueue;
    private static NetworkUtils instance;
    private static ProgressDialog pd;
    private Context ctx;

    //functia cu care apelam clasa in proiect, pentru a avea o singura instanta a clasei oriunde.
    public static NetworkUtils getNetworkUtils(Context ctx) {
        if (instance == null) {
            instance = new NetworkUtils(ctx);
        }
        return instance;
    }

    //constructorul clasei
    private NetworkUtils(Context ctx) {
        this.ctx = ctx;
        requestQueue = Volley.newRequestQueue(ctx);
    }

    //functia care face requestul de canale catre server
    //primeste parametrii numele sau id-ul introduse de utilizator si tipul (nume sau id) pentru a apela url-ul corespunzator
    //interfata onCompleteListener lanseaza o actiune atunci cand o alta actiune a fost terminata
    public void getChannelsList(String name,String type,  final OnCompleteListener onCompleteListener) throws UnsupportedEncodingException {
        String path;
        if(type.equals("name")){
            path = buildChannelsListPath(name);
        }else{
            path =buildChannelsIdListPath(name);
        }
        StringRequest request = new StringRequest(Request.Method.GET, path
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "get Channels List response : " + response);
                ArrayList<Channel>channels = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray items = jsonObject.getJSONArray("items");
                    for(int i = 0; i<items.length(); i++){
                        Channel channel = new Channel();
                        JSONObject item = items.getJSONObject(i);
                        channel.id = item.getString("id");

                        JSONObject snippet = item.getJSONObject("snippet");
                        channel.title = snippet.getString("title");
                        channel.description = snippet.getString("description");
                        JSONObject thumb = snippet.getJSONObject("thumbnails");
                        JSONObject def = thumb.getJSONObject("high");
                        channel.default_thumb = def.getString("url");

                        JSONObject contentDetails = item.getJSONObject("contentDetails");
                        JSONObject relatedPlaylists = contentDetails.getJSONObject("relatedPlaylists");
                        channel.uploads_id = relatedPlaylists.getString("uploads");
                        channels.add(channel);

                    }
                    Log.d(TAG,"channels:"+channels.toString());
                    Channels channelsObj = new Channels();
                    channelsObj.channels.addAll(channels);
                    onCompleteListener.onComplete(true, channelsObj);

                }catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                onCompleteListener.onComplete(false, null);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(10 * 1000, 1, 1));
        requestQueue.add(request);
    }
    // la fel ca functia pentru canale
    public void getUploadedVideos(String channelId, final OnCompleteListener onCompleteListener) {
        StringRequest request = new StringRequest(Request.Method.GET, buildPlaylistsListPath(channelId, 50)
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "get playlists response : " + response);
                ArrayList<Video>videos = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray items = jsonObject.getJSONArray("items");
                    for(int i = 0; i<items.length(); i++){
                        Video video = new Video();
                        JSONObject item = items.getJSONObject(i);

                        JSONObject snippet = item.getJSONObject("snippet");
                        video.title = snippet.getString("title");
                        video.description = snippet.getString("description");
                        JSONObject thumb = snippet.getJSONObject("thumbnails");
                        JSONObject def = thumb.getJSONObject("high");
                        video.default_thumb = def.getString("url");
                        JSONObject resourceId = snippet.getJSONObject("resourceId");
                        video.id = resourceId.getString("videoId");

                        videos.add(video);

                    }
                    Log.d(TAG,"videos:"+videos.toString());
                    Videos videosObj = new Videos();
                    videosObj.videos.addAll(videos);
                    onCompleteListener.onComplete(true, videosObj);

                }catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                onCompleteListener.onComplete(false, null);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(10 * 1000, 1, 1));
        requestQueue.add(request);
    }

    //functiile cu ajutorul carora construim link-ul pentru requestul la serverul google
    private String buildChannelsListPath( String channelName) throws UnsupportedEncodingException {
        String path = Constants.CHANNELS_LIST_URL;
        String part = "part=contentDetails%2Csnippet";
        StringBuilder query = new StringBuilder();
        query.append("forUsername=");
        query.append(URLEncoder.encode(channelName, "UTF-8"));
        String key = "key="+Constants.browserKey;
        path += part + "&" + query + "&" + key;
        Log.d(TAG, "Request link " + path);
        return path;
    }

    private String buildChannelsIdListPath( String channelId) throws UnsupportedEncodingException {
        String path = Constants.CHANNELS_LIST_URL;
        String part = "part=contentDetails%2Csnippet";
        String id = "id=" + channelId;
        String key = "key="+Constants.browserKey;
        path += part + "&" + id + "&" + key;
        Log.d(TAG, "Request link " + path);
        return path;
    }

    private String buildPlaylistsListPath(String channel_id, int maxResults) {

        String path = Constants.PLAYLIST_ITEMS_URL;
        String part = "part=snippet";
        String max_results = "maxResults="+maxResults;
        String name = "playlistId=" + channel_id;
        String key = "key="+Constants.browserKey;
        path += part + "&" + max_results+ "&" + name + "&" + key;
        Log.d(TAG, "Request link " + path);
        return path;
    }

    //functia care afiseaza popup-ul pentru asteptare
    public static void showProgressDialog(Context context) {

        pd = new ProgressDialog(context);

        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(context.getString(R.string.please_wait));
        pd.setCancelable(false);
        pd.setIndeterminate(true);

        pd.show();

    }
    //functia care ascunde popup-ul
    public static void hideProgressDialog() {

        if (pd != null && pd.isShowing())
            pd.dismiss();

    }
}
