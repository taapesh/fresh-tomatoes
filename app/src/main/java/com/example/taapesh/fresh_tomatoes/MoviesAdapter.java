package com.example.taapesh.fresh_tomatoes;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MoviesAdapter extends ArrayAdapter<Movie> {
    public MoviesAdapter(Context context, ArrayList<Movie> aMovies) {
        super(context, 0, aMovies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Movie movie = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.adapter_item_movie, null);
        }
        // Lookup view for data population
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        TextView tvCriticsScore = (TextView) convertView.findViewById(R.id.tvCriticsScore);
        TextView tvCast = (TextView) convertView.findViewById(R.id.tvCast);
        ImageView ivScoreLogo = (ImageView) convertView.findViewById(R.id.ivScoreLogo);
        ImageView ivPosterImage = (ImageView) convertView.findViewById(R.id.ivPosterImage);
        // Populate the data into the template view using the data object
        tvTitle.setText(movie.getTitle());

        int compositeScore = movie.getCompositeScore();
        tvCriticsScore.setText(compositeScore + "%");

        if (compositeScore >= 60) {
            ivScoreLogo.setImageResource(R.drawable.fresh);
        } else {
            ivScoreLogo.setImageResource(R.drawable.rotten);
        }

        tvCast.setText(movie.getCastList());

        Picasso.with(getContext()).load(movie.getPosterUrl())
            .placeholder(R.drawable.small_movie_poster)
            .into(ivPosterImage);

        // Return the completed view to render on screen
        return convertView;
    }
}