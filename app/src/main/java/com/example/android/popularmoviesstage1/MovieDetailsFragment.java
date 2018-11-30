package com.example.android.popularmoviesstage1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetailsFragment extends Fragment {
    private TextView title_tv, rating_tv, release_date_tv, synopsis_tv;
    private ImageView image_iv;
    private String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/";
    private String IMAGE_SIZE = "w500";
    private String MOVIE = "MOVIE";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_movie_details_fragment, container, false);
        Movie movies = getActivity().getIntent().getParcelableExtra(MOVIE);

        title_tv = (TextView) view.findViewById(R.id.title);
        rating_tv = (TextView) view.findViewById(R.id.rating);
        release_date_tv = (TextView) view.findViewById(R.id.release_date);
        synopsis_tv = (TextView) view.findViewById(R.id.synopsis);
        image_iv = (ImageView) view.findViewById(R.id.image);

        // Get the movie details (see Movie class for get methods)
        if(movies != null) {
            title_tv.setText(movies.getTitle());
            rating_tv.setText(movies.getRating());
            release_date_tv.setText(movies.getReleaseDate());
            synopsis_tv.setText(movies.getSynopsis());
            // Create method to use picasso to load/set the image
            setImage(movies.getImagePath());
        }

        return view;
    }

    public MovieDetailsFragment() {
    }

    private void setImage(String string) {
        String imagePathBuilder = new StringBuilder().append(BASE_IMAGE_URL).append(IMAGE_SIZE).append(string).toString();

        Picasso.with(getContext()).load(imagePathBuilder).placeholder(R.drawable.placeholder).error(R.drawable.error)
                .into(image_iv);
    }
}