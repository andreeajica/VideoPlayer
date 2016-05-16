package com.dizertatie.videoplayer.objects;

import java.io.Serializable;

//obiectul de tip channel care primeste datele despre un canal
//serializabil este o interfata care permite informatiei din obiect sa fie trimisa prin intent intre activitati
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
