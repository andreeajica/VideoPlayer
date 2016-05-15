package com.dizertatie.videoplayer.objects;

import java.io.Serializable;

/**
 * Created by Andreea on 5/14/2016.
 */
public class Channel implements Serializable{

    public String id;
    public String title;
    public String description;
    public String default_thumb;
    public String uploads_id;

    @Override
    public String toString() {
        String s =id+", "
                +title+", "
                +description+", "
                +default_thumb+", "
                +uploads_id;
        return s;
    }
}
