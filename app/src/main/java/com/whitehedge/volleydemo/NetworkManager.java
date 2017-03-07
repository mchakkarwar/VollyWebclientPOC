package com.whitehedge.volleydemo;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by Mahesh Chakkarwar on 07-07-2016.
 */
public class NetworkManager {
    public static final String TAG = NetworkManager.class
            .getSimpleName();
    private Context context;
    private static NetworkManager networkManager;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;
    private LruBitmapCache lruBitmapCache;

    private NetworkManager(Context context) {
        this.context = context;
    }

    public static NetworkManager getInstance(Context context) {
        if (networkManager == null)
            networkManager = new NetworkManager(context);
        return networkManager;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }

        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (requestQueue != null) {
            requestQueue.cancelAll(tag);
        }
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (imageLoader == null) {
            imageLoader = new ImageLoader(this.requestQueue, getBitmapCache());
        }
        return this.imageLoader;
    }

    public LruBitmapCache getBitmapCache() {
        if (lruBitmapCache == null)
            lruBitmapCache = new LruBitmapCache();
        return lruBitmapCache;
    }
}
