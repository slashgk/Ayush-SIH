package com.bitplasma.app.ayush_demo;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by slash on 24-03-2018.
 */

public class AyurvedaLoader extends AsyncTaskLoader<Ayurveda> {
    private String mUrl;
    public static final String LOG_TAG = AyurvedaLoader.class.getName();

    public AyurvedaLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        Log.i(LOG_TAG,"TEST: onStartLoading called");
        forceLoad();
    }

    @Override
    public Ayurveda loadInBackground() {
        Log.i(LOG_TAG,"TEST: loadInBackground called");
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of earthquakes.
        Ayurveda ayurvedas = QueryUtils.fetchAyurvedaData(mUrl);
        return ayurvedas;
    }
}
