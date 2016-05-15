package com.dizertatie.videoplayer.objects;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Andreea on 5/14/2016.
 */
public class Videos implements Serializable {

    public ArrayList<Video> videos = new ArrayList<>();


    @Override
    public String toString() {
        return videos +"";
    }
}
