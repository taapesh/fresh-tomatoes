package com.example.taapesh.fresh_tomatoes;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class RottenTomatoesClient {
    private final String API_KEY = "9htuhtcb4ymusd73d4z6jxcj";
    private final String MOVIEDB_API_KEY = "482fe95b9d22ad106c308b0bdb75911d";
    private final String API_BASE_URL = "http://api.rottentomatoes.com/api/public/v1.0/";
    private final String MOVIEDB_BASE = "https://api.themoviedb.org/3/search/movie";
    public final static String THUMBNAIL_BASE = "http://image.tmdb.org/t/p/w185";
    public final static String POSTER_BASE = "http://image.tmdb.org/t/p/w342";

    private AsyncHttpClient client;

    public RottenTomatoesClient() {
        this.client = new AsyncHttpClient();
    }

    public void getBoxOfficeMovies(JsonHttpResponseHandler handler) {
        String url = getApiUrl("lists/movies/box_office.json");
        RequestParams params = new RequestParams("apikey", API_KEY);
        client.get(url, params, handler);
    }

    private String getApiUrl(String relativeUrl) {
        return API_BASE_URL + relativeUrl;
    }

    public void getMoviePosterUrl(String title, JsonHttpResponseHandler handler) {
        RequestParams params = new RequestParams("query", title);
        params.add("api_key", MOVIEDB_API_KEY);
        client.get(MOVIEDB_BASE, params, handler);
    }
}
