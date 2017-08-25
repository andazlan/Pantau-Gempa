package com.andazlan.pantaugempa;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Earthquake>> {
    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final int EARTHQUAKE_LOADER_ID = 1;

    private static final String USGS_RECENT_EARTHQUAKE =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=1&limit=20";

    private TextView emptyEarthquake;
    private ProgressBar loading;

    public EarthquakeAdapter earthquakeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //List<Earthquake> earthquakes = QueryUtils.extractEarthquakes();

        emptyEarthquake = (TextView) findViewById(R.id.txt_empty);
        loading = (ProgressBar) findViewById(R.id.prb_loading);
        earthquakeAdapter = new EarthquakeAdapter(this);

        ListView earthquakeListView = (ListView) findViewById(R.id.list_gempa);
        earthquakeListView.setEmptyView(emptyEarthquake);
        earthquakeListView.setAdapter(earthquakeAdapter);
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Earthquake currentEarthquake = earthquakeAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri earthquakeUri = Uri.parse(currentEarthquake.getUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });

        //EarthquakeAsyncTask earthquakeAsyncTask = new EarthquakeAsyncTask();
        //earthquakeAsyncTask.execute(USGS_RECENT_EARTHQUAKE);

        if (isConnected()){
            getSupportLoaderManager().initLoader(EARTHQUAKE_LOADER_ID, null, this);
        }
        else {
            emptyEarthquake.setText(getString(R.string.not_connected_to_the_network));
            loading.setVisibility(View.GONE);
        }

    }

    @Override
    public Loader<List<Earthquake>> onCreateLoader(int id, Bundle args) {
        Log.d(LOG_TAG, "onCreateLoader");
        return new EarthquakeLoader(this, USGS_RECENT_EARTHQUAKE);
    }

    @Override
    public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> data) {
        Log.d(LOG_TAG, "onLoadFinished");
        loading.setVisibility(View.GONE);
        emptyEarthquake.setText(getString(R.string.no_earthquake_found));
        earthquakeAdapter.clear();
        if (data != null && !data.isEmpty()){
            earthquakeAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Earthquake>> loader) {
        Log.d(LOG_TAG, "onLoaderReset");
        earthquakeAdapter.clear();
    }

    public boolean isConnected(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected =  activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    /*
    private class EarthquakeAsyncTask extends AsyncTask<String, Void, List<Earthquake>>{

        @Override
        protected List<Earthquake> doInBackground(String... params) {
            if (params.length < 1 || params[0] == null){
                return  null;
            }
            List<Earthquake> earthquakeList = QueryUtils.fetchEarthquakeData(params[0]);
            return earthquakeList;
        }

        @Override
        protected void onPostExecute(List<Earthquake> earthquakes) {
            earthquakeAdapter.clear();
            if (earthquakes != null && !earthquakes.isEmpty()){
                earthquakeAdapter.addAll(earthquakes);
            }
        }
    }
    */
}
