package com.example.android.popularmoviesstage1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends BaseAdapter {

    private final Context mContext;
    private final List<Movie> mMovies;
    private final static String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/";
    private final static String IMAGE_SIZE = "w500";

    MovieAdapter(Context context, List<Movie> movies) {
        this.mContext = context;
        this.mMovies = movies;
    }

    // Clears the list
    void clear() {
        if (mMovies.size() > 0) {
            mMovies.clear();
        }
    }

    @Override
    public int getCount() {
        return mMovies.size();
    }

    @Override
    public Object getItem(int position) {
        return mMovies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = (Movie) getItem(position);
        ImageView imageView;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            imageView = (ImageView) inflater.inflate(R.layout.movie_item, parent, false);
        } else {
            imageView = (ImageView) convertView;
        }

        String url = new StringBuilder().append(BASE_IMAGE_URL).append(IMAGE_SIZE).append(movie.getImagePath().trim()).toString();

        Picasso.with(mContext).load(url).placeholder(R.drawable.placeholder).error(R.drawable.error).into(imageView);
        return imageView;
    }
}