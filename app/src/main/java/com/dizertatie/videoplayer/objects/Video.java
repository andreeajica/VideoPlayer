package com.dizertatie.videoplayer.objects;

import java.io.Serializable;

/**
 * Created by Andreea on 5/14/2016.
 */
public class Video implements Serializable {

    public String id;
    public String title;
    public String description;
    public String default_thumb;

    @Override
    public String toString() {
        String s =id+", "
                +title+", "
                +description+", "
                +default_thumb;

        return s;
    }
}
