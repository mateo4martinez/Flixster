package com.example.flixster.models;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.flixster.R;

import org.parceler.Parcels;

public class MovieDetailsActivity extends AppCompatActivity {

    // the movie for display
    Movie movie;

    // the view objects
    TextView tvTitle;
    TextView tvOverview;
    RatingBar rbVoteAverage;
    ImageView detPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        // resolve the view objects
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvOverview = (TextView) findViewById(R.id.tvOverview);
        rbVoteAverage = (RatingBar) findViewById(R.id.rbVoteAverage);
        detPoster = (ImageView) findViewById(R.id.detPoster);

        // unwrap the movie passed in via intent
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));

        // set the title and overview
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());

        // Vote avg is from 0-10, convert to 0-5 by dividing by 2
        float voteAverage = movie.getVoteAverage().floatValue();
        rbVoteAverage.setRating(voteAverage / 2.0f);

        // Set the image and get context
        Context context = getApplicationContext();
        String imageUrl;
        int placeholderKey;

        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // then imageUrl = back drop image
            imageUrl = movie.getBackdropPath();
            placeholderKey = R.drawable.backdrop;
        } else {
            // else imageUrl = poster image
            imageUrl = movie.getPosterPath();
            placeholderKey = R.drawable.placeholder;
        }

        Glide.with(context).load(imageUrl).placeholder(placeholderKey).into(detPoster);
    }
}