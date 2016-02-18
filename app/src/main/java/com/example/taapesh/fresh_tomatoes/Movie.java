package com.example.taapesh.fresh_tomatoes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Movie implements Serializable {
    private String title;
    private String synopsis;
    private String posterUrl;
    private String largePosterUrl;
    private int audienceScore;
    private int criticsScore;
    private int year;
    private ArrayList<String> castList;

    // Returns a Movie object given the expected JSON
    public static Movie fromJson(JSONObject jsonObject) {
        Movie m = new Movie();
        try {
            m.title = jsonObject.getString("title");
            m.year = jsonObject.getInt("year");
            m.synopsis = jsonObject.getString("synopsis");
            m.criticsScore = jsonObject.getJSONObject("ratings").getInt("critics_score");
            m.audienceScore = jsonObject.getJSONObject("ratings").getInt("audience_score");
            m.castList = new ArrayList<String>();
            JSONArray abridgedCast = jsonObject.getJSONArray("abridged_cast");
            for (int i = 0; i < abridgedCast.length(); i++) {
                m.castList.add(abridgedCast.getJSONObject(i).getString("name"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return m;
    }

    // Decodes array of box office movie json results into movie model objects
    public static ArrayList<Movie> fromJson(JSONArray jsonArray) {
        ArrayList<Movie> movies = new ArrayList<Movie>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject moviesJson = null;
            try {
                moviesJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            Movie movie = Movie.fromJson(moviesJson);
            if (movie != null) {
                movies.add(movie);
            }
        }

        return movies;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public int getCriticsScore() {
        return criticsScore;
    }

    public String getCastList() {
        StringBuilder sb = new StringBuilder();

        for (String name: castList) {
            sb.append(name);
            sb.append(", ");
        }
        sb.setLength(sb.length() - 2);
        return sb.toString();
    }

    public String getLargePosterUrl() {
        return largePosterUrl;
    }

    public int getAudienceScore() {
        return audienceScore;
    }

    public int getCompositeScore() {
        return (audienceScore + criticsScore) / 2;
    }

    public void setPosterUrls(String filePath) {
        posterUrl = RottenTomatoesClient.THUMBNAIL_BASE + filePath;
        largePosterUrl = RottenTomatoesClient.POSTER_BASE + filePath;
    }
}