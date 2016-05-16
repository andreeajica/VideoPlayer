package com.dizertatie.videoplayer.utils;


public class Constants {

    //api-key-ul aplicatiei primit din consola google
    public static String browserKey = "AIzaSyBpFQrbKC_NWvnjMu_rkq7UKWaNcsAAHdo";

    //url-urile folosite pentru requesturile catre server
    public static String BASE_URL = "https://www.googleapis.com/youtube/v3/";
    public static String CHANNELS_LIST_URL = BASE_URL + "channels?";
    public static String PLAYLIST_ITEMS_URL =BASE_URL+ "playlistItems?";

    //cheile pentru salvarea datelor in memoria cache
    public static String SHARED_PREFS = "shared_prefs";
    public static String CHANNEL_NAME = "channel_name";

}