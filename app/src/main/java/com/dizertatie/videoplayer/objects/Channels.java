package com.dizertatie.videoplayer.objects;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Andreea on 5/14/2016.
 */
public class Channels implements Serializable {
    public ArrayList<Channel>channels = new ArrayList<>();

    @Override
    public String toString() {
        return channels +"";
    }
}
