package com.andazlan.pantaugempa;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andazlan on 8/25/17.
 */

public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {
    private static final String LOG_TAG = EarthquakeLoader.class.getSimpleName();
    private String mUrl;

    public EarthquakeLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }


    @Override
    public List<Earthquake> loadInBackground() {
        Log.d(LOG_TAG, "onStartLoading");
        if (mUrl == null){
            return null;
        }

        List<Earthquake> earthquakeList = QueryUtils.fetchEarthquakeData(mUrl);
        return earthquakeList;
    }

    @Override
    protected void onStartLoading() {
        Log.d(LOG_TAG, "onStartLoading");
        forceLoad();
    }
}
