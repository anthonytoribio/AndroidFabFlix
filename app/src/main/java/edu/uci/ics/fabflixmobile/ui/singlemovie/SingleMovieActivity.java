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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
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

//    private Movie movie;
    private ArrayList<Movie> Movies = new ArrayList<Movie>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singlemovie);
        // TODO: this should be retrieved from the backend server
        Intent intent = getIntent();
        String movieId = intent.getStringExtra("id");
        Log.d("SingleMovieActivity", "Selected movie's id: " + movieId);
        getMovieInfo(movieId);
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

                    createMovie(response);
                    updatePageView();

                },
                error -> {
                    // error
                    Log.d("SingleMovieActivity GET Error: ", error.toString());
                }) {
        };
        queue.add(movieRequest);
    }

    public void createMovie(String jsonString) {

        JSONArray array = new JSONArray();
        JSONObject object = new JSONObject();
        //Get the json array
        try {

            array = new JSONArray(jsonString);
            object = array.getJSONObject(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }



        try {

            Log.d("SingleMovieActivity", "The Json object is :" + object );

            String title = object.getString("movie_title");
            String id = object.getString("movie_id");
            Short year = Short.parseShort(object.getString("release_year"));
            String rating = object.getString("rating");
            String director = object.getString("director");
            ArrayList<String> genres = new ArrayList<>(Arrays.asList(object.getString("genres").split(",")));
            ArrayList<String> actors = new ArrayList<>(Arrays.asList(object.getString("starNames").split(",")));
            Movie movie = new Movie(title, id, rating, year, director, genres, actors);
            Movies.add(movie);
            Log.d("SingleMovieActivity", "Movies after adding movie is: " + Movies);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void updatePageView() {
        Log.d("SingleMovieActivity","Movies is: " + Movies);
        SingleMovieViewAdapter adapter = new SingleMovieViewAdapter(this, Movies);
        ListView listView = findViewById(R.id.singlemovielist);
        listView.setAdapter(adapter);

    }
}
