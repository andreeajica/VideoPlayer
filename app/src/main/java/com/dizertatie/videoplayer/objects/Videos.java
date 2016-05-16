package com.dizertatie.videoplayer.objects;

import java.io.Serializable;
import java.util.ArrayList;

//obiectul in care se salveaza lista de clipuri
public class Videos implements Serializable {

    public ArrayList<Video> videos = new ArrayList<>();


    @Override
    public String toString() {
        return videos +"";
    }
}
