package com.example.android.popularmoviesstage1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private MovieAdapter mMovieAdapter;
    private ArrayList<Movie> mMovieList;
    private String sortOrder;
    private static final String MOVIE = "MOVIE";
    private static final String MOVIE_LIST = "MOVIE_LIST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null || !savedInstanceState.containsKey(MOVIE_LIST)) {
            mMovieList = new ArrayList<>();
        } else {
            mMovieList = savedInstanceState.getParcelableArrayList(MOVIE_LIST);
        }

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mMovieAdapter = new MovieAdapter(this, mMovieList);
        GridView gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(mMovieAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = (Movie) mMovieAdapter.getItem(position);
                Intent intent = new Intent(MainActivity.this, MovieDetails.class);
                intent.putExtra(MOVIE, movie);
                startActivity(intent);
            }
        });

        fetchMovies();
    }

    @Override
    public void onSaveInstanceState(Bundle outOfState) {
        outOfState.putParcelableArrayList(MOVIE_LIST, mMovieList);
        super.onSaveInstanceState(outOfState);
    }

    private void fetchMovies() {
        FetchMoviesTask fetchMoviesTask = new FetchMoviesTask(new MovieUpdater() {
            @Override
            public void movieUpdaterAdapter(Movie[] movies) {
                if(movies != null) {
                    mMovieAdapter.clear();
                    Collections.addAll(mMovieList, movies);
                    mMovieAdapter.notifyDataSetChanged();
                }
            }
        });

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sortOrder = sharedPreferences.getString
                (getString(R.string.shared_pref_sort_key), getString(R.string.shared_pref_default_pop_value));
                fetchMoviesTask.execute(sortOrder);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if(itemId == R.id.settings) {
            startActivity(new Intent(MainActivity.this, Settings.class));
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onStart() {
        super.onStart();
        fetchMovies();
    }
}