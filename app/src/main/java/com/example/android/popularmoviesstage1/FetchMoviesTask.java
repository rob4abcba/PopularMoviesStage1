package com.example.android.popularmoviesstage1;

import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchMoviesTask extends AsyncTask<String, Void, Movie[]> {

    private final MovieUpdater movieUpdaterTask;
    final String REQUEST_GET = "GET";

    FetchMoviesTask(MovieUpdater movieUpdaterTask) {
        this.movieUpdaterTask = movieUpdaterTask;
    }

    private Movie[] getMoviesJson(String stringMovieJson) throws JSONException {
        final String TITLE = "original_title";
        final String IMAGE_PATH = "poster_path";
        final String SYNOPSIS = "overview";
        final String RATING = "vote_average";
        final String RELEASE_DATE = "release_date";

        // Check to see if null or empty string
        if(stringMovieJson == null || "".equals(stringMovieJson)) {
            return null;
    }

        JSONObject jsonObject = new JSONObject(stringMovieJson);
        JSONArray jsonArray = jsonObject.getJSONArray("results");
        Movie[] movies = new Movie[jsonArray.length()];
        for(int i = 0; i < jsonArray.length(); i++) {
            JSONObject movieJSONObject = jsonArray.getJSONObject(i);
            movies[i] = new Movie(movieJSONObject.getString(TITLE),
                    movieJSONObject.getString(IMAGE_PATH),
                    movieJSONObject.getString(SYNOPSIS),
                    movieJSONObject.getString(RATING),
                    movieJSONObject.getString(RELEASE_DATE));
            }
        return movies;
    }

    @Override
    protected Movie[] doInBackground(String... strings) {
        if(strings.length == 0) {
            return null;
        }
        final String BASE_URL = "https://api.themoviedb.org/3/movie/";
        final String API_KEY = "api_key";

        HttpURLConnection urlConnection = null;
        String stringMovieJson = null;
        BufferedReader reader = null;

        Uri uri = Uri.parse(BASE_URL).buildUpon().appendEncodedPath(strings[0])
                .appendQueryParameter(API_KEY, BuildConfig.MOVIE_API_KEY).build();

        try {
            URL url = new URL(uri.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(REQUEST_GET);
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder stringBuilder = new StringBuilder();
            if(inputStream == null) {
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }

            if(stringBuilder.length() == 0) {
                return null;
            }

            stringMovieJson = stringBuilder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if(reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            return getMoviesJson(stringMovieJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Movie[] movies) {
        movieUpdaterTask.movieUpdaterAdapter(movies);
    }
}