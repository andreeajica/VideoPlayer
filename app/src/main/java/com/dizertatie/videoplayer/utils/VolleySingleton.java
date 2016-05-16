package com.dizertatie.videoplayer.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

// clasa volley cu ajutorul careia afisam imaginile primite de la server printr-un url (pentru canal sau pentru clipuri)
public class VolleySingleton {

    private static RequestQueue mInstance;
    private static ImageLoader mImageLoader;

    static class BitmapLruCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {
        public BitmapLruCache() {
            this(getDefaultLruCacheSize());
        }

        public BitmapLruCache(int sizeInKiloBytes) {
            super(sizeInKiloBytes);
        }

        @Override
        protected int sizeOf(String key, Bitmap value) {
            return value.getRowBytes() * value.getHeight() / 1024;
        }

        @Override
        public Bitmap getBitmap(String url) {
            return get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            put(url, bitmap);
        }

        public static int getDefaultLruCacheSize() {
            final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
            return maxMemory / 8;
        }
    }

    /**
     * Get the RequestQueue used in networking.
     *
     * @param context
     * @return - instance of RequestQueue
     */
    public static RequestQueue getRequestQueue(Context context) {
        if (mInstance == null)
            mInstance = Volley.newRequestQueue(context);
        return mInstance;
    }

    /**
     * Get the ImageLoader used with NetworkImageView to load images from requests.
     *
     * @param context
     * @return - instance of ImageLoader
     */
    public static ImageLoader getImageLoader(Context context) {
        if (mImageLoader == null) {
            ImageLoader.ImageCache imageCache = new BitmapLruCache();
            mImageLoader = new ImageLoader(getRequestQueue(context), imageCache);
        }
        return mImageLoader;
    }
}
