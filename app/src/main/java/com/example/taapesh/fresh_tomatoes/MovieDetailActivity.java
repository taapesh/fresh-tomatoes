package com.example.taapesh.fresh_tomatoes;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {
    private ImageView ivPosterImage;
    private TextView tvTitle;
    private TextView tvSynopsis;
    private TextView tvCast;
    private TextView tvAudienceScore;
    private TextView tvCriticsScore;
    private ImageView ivAudienceLogo;
    private ImageView ivCriticsLogo;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ivPosterImage = (ImageView) findViewById(R.id.ivPosterImage);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvSynopsis = (TextView) findViewById(R.id.tvSynopsis);
        tvCast = (TextView) findViewById(R.id.tvCast);
        tvAudienceScore =  (TextView) findViewById(R.id.tvAudienceScore);
        tvCriticsScore = (TextView) findViewById(R.id.tvCriticsScore);
        ivAudienceLogo = (ImageView) findViewById(R.id.ivAudienceLogo);
        ivCriticsLogo = (ImageView) findViewById(R.id.ivCriticsLogo);
        // Load movie data
        Movie movie = (Movie) getIntent().getSerializableExtra(MoviesActivity.MOVIE_DETAIL_KEY);
        loadMovie(movie);
    }

    // Populate the data for the movie
    @SuppressLint("NewApi")
    public void loadMovie(Movie movie) {
        if (android.os.Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.HONEYCOMB) {
            if (actionBar != null) {
                actionBar.setTitle(movie.getTitle());
            }
        }
        // Populate data
        int criticsScore = movie.getCriticsScore();
        int audienceScore = movie.getAudienceScore();

        if (criticsScore >= 60) {
            ivCriticsLogo.setImageResource(R.drawable.fresh);
        } else {
            ivCriticsLogo.setImageResource(R.drawable.rotten);
        }
        if (audienceScore >= 60) {
            ivAudienceLogo.setImageResource(R.drawable.audience_fresh);
        } else {
            ivAudienceLogo.setImageResource(R.drawable.audience_rotten);
        }

        tvTitle.setText(movie.getTitle());
        tvCriticsScore.setText(criticsScore + "%");
        tvAudienceScore.setText(audienceScore + "%");
        tvCast.setText(movie.getCastList());
        tvSynopsis.setText(movie.getSynopsis());

        Picasso.with(this).load(movie.getLargePosterUrl()).
                placeholder(R.drawable.large_movie_poster).
                into(ivPosterImage);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
