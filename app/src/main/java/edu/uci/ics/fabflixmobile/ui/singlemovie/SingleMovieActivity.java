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

    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movielist);
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

        //Get the json array
        JSONArray jsonResponse = new JSONArray();
        try {
            jsonResponse = new JSONArray(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            JSONObject object = jsonResponse.getJSONObject(0);
            Log.d("SingleMovieActivity", "The Json object is :" + object );

            String title = object.getString("movie_title");
            String id = object.getString("movie_id");
            Short year = Short.parseShort(object.getString("release_year"));
            String director = object.getString("director");
            ArrayList<String> genres = new ArrayList<>(Arrays.asList(object.getString("genres").split(",")));
            ArrayList<String> actors = new ArrayList<>(Arrays.asList(object.getString("starNames").split(",")));
            movie = new Movie(title, id, year, director, genres, actors);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void updatePageView() {

    }
}
