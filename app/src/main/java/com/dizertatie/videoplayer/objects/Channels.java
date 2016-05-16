package com.dizertatie.videoplayer.objects;

import java.io.Serializable;
import java.util.ArrayList;

//obiectul care retine lista de canale
public class Channels implements Serializable {
    public ArrayList<Channel>channels = new ArrayList<>();

    @Override
    public String toString() {
        return channels +"";
    }
}
