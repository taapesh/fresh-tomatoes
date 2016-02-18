package com.example.taapesh.fresh_tomatoes;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import cz.msebera.android.httpclient.Header;

public class MoviesActivity extends Activity {
    private ListView lvMovies;
    private MoviesAdapter adapterMovies;
    private RottenTomatoesClient client;
    public static final String MOVIE_DETAIL_KEY = "movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        lvMovies = (ListView) findViewById(R.id.lvMovies);
        setupListHeader();
        ArrayList<Movie> aMovies = new ArrayList<Movie>();
        adapterMovies = new MoviesAdapter(this, aMovies);
        lvMovies.setAdapter(adapterMovies);
        // Fetch the data remotely
        fetchBoxOfficeMovies();
        setupMovieSelectedListener();
    }

    private void fetchBoxOfficeMovies() {
        client = new RottenTomatoesClient();
        client.getBoxOfficeMovies(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray items = null;
                try {
                    // Get the movies json array
                    items = response.getJSONArray("movies");
                    // Parse json array into array of model objects
                    ArrayList<Movie> movies = Movie.fromJson(items);
                    // Load model objects into the adapter which displays them
                    adapterMovies.addAll(movies);
                    getMoviePosters(movies);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getMoviePosters(ArrayList<Movie> movies) {
        client = new RottenTomatoesClient();
        for (final Movie m: movies) {
            client.getMoviePosterUrl(m.getTitle(), new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        JSONObject json = (JSONObject) response.getJSONArray("results").get(0);
                        m.setPosterUrls(json.getString("poster_path"));
                        adapterMovies.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public void setupMovieSelectedListener() {
        lvMovies.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View item, int position, long rowId) {
                if (position == 0) return;
                Intent i = new Intent(MoviesActivity.this, MovieDetailActivity.class);
                i.putExtra(MOVIE_DETAIL_KEY, adapterMovies.getItem(position-1));
                startActivity(i);
            }
        });
    }

    private void setupListHeader() {
        TextView tvHeader = new TextView(this);
        tvHeader.setText("What's Fresh");
        tvHeader.setTextSize(20);
        tvHeader.setPadding(0, 40, 0, 40);
        tvHeader.setGravity(Gravity.CENTER);
        lvMovies.addHeaderView(tvHeader);
    }

}