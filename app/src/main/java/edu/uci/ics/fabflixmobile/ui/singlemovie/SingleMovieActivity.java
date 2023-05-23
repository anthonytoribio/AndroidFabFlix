package edu.uci.ics.fabflixmobile.ui.singlemovie;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.widget.ListView;
import android.widget.Toast;
import android.content.Intent;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import edu.uci.ics.fabflixmobile.R;
import edu.uci.ics.fabflixmobile.data.NetworkManager;
import edu.uci.ics.fabflixmobile.data.model.Movie;
import edu.uci.ics.fabflixmobile.ui.movielist.MovieListViewAdapter;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SingleMovieActivity extends AppCompatActivity {

    /*
    In Android, localhost is the address of the device or the emulator.
    To connect to your machine, you need to use the below IP address
    */

    private final String host = "10.0.2.2";
    private final String port = "8080";
    private final String domain = "FabFlix";
    private final String baseURL = "http://" + host + ":" + port + "/" + domain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movielist);
        // TODO: this should be retrieved from the backend server
        Intent intent = getIntent();
        String movieId = intent.getStringExtra("id");
        Log.d("SingleMovieActivity", "Selected movie's id: " + movieId);
        getMovieInfo(movieId);

//        final ArrayList<Movie> movies = new ArrayList<>();
//        movies.add(new Movie("The Terminal", (short) 2004));
//        movies.add(new Movie("The Final Season", (short) 2007));
//        MovieListViewAdapter adapter = new MovieListViewAdapter(this, movies);
//        ListView listView = findViewById(R.id.list);
//        listView.setAdapter(adapter);
//        listView.setOnItemClickListener((parent, view, position, id) -> {
//            Movie movie = movies.get(position);
//            @SuppressLint("DefaultLocale") String message = String.format("Clicked on position: %d, name: %s, %d", position, movie.getName(), movie.getYear());
//            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
//        });
    }

    @SuppressLint("SetTextI18n")
    public void getMovieInfo(String movieId) {

        final RequestQueue queue = NetworkManager.sharedManager(this).queue;
        Log.d("SingleMovieActivity", movieId);

        // request type is GET
        final StringRequest movieRequest = new StringRequest(
                Request.Method.GET,
                baseURL + "/api/single-movie?id=" + movieId,
                response -> {
                    //Parse the json response
                    JSONObject jsonResponse;
                    Log.d("SingleMovieActivity jsonResponse: ", response);

                },
                error -> {
                    // error
                    Log.d("SingleMovieActivity GET Error: ", error.toString());
                }) {
        };
        queue.add(movieRequest);
    }
}
