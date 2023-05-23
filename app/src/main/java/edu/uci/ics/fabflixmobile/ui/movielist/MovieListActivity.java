package edu.uci.ics.fabflixmobile.ui.movielist;

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
import edu.uci.ics.fabflixmobile.ui.main.MainActivity;
import edu.uci.ics.fabflixmobile.ui.singlemovie.SingleMovieActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;


public class MovieListActivity extends AppCompatActivity {

    /*
      In Android, localhost is the address of the device or the emulator.
      To connect to your machine, you need to use the below IP address
     */
    private final String host = "10.0.2.2";
    private final String port = "8080";
    private final String domain = "FabFlix";
    private final String baseURL = "http://" + host + ":" + port + "/" + domain;

    private Integer offset = 0;
    private ArrayList<Movie> Movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movielist);
        // TODO: this should be retrieved from the backend server
        Intent intent = getIntent();
        String payload = intent.getStringExtra("payload");
        Log.d("MovieListActivity", "payload is: " + payload);
        getMovies(payload, offset);

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
    public void getMovies(String query, Integer offset) {

        final RequestQueue queue = NetworkManager.sharedManager(this).queue;
        Log.d("MovieListActivity", query);

        String urlParams = String.format("?fullSearch=true&payload=%s&pageNum=1&sort1=Rating&sortOption1=DESC&numResults=25&offset=%d", query, offset);

        // request type is GET
        final StringRequest movieRequest = new StringRequest(
                Request.Method.GET,
                baseURL + "/api/movies-list" + urlParams,
                response -> {
                    createArray(response);
                    updateListView();
                },
                error -> {
                    // error
                    Log.d("MovieListActivity", error.toString());
                }) {
        };
        queue.add(movieRequest);
    }

    public void createArray(String jsonString) {
        Log.d("MovieListActivity", jsonString);
        Movies = new ArrayList<Movie>();

        //Get the json array
        JSONArray jsonResponse = new JSONArray();
        try {
            jsonResponse = new JSONArray(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Log the array length
        Log.d("MovieListActivity", "The Length of Json Response is " + jsonResponse.length());

        // for each json object in array make a Movie object and append to Movies array
        for (Integer i = 0; i < jsonResponse.length(); i++) {
            try {
                JSONObject object = jsonResponse.getJSONObject(i);
                Log.d("MovieListActivity", "The Json object is :" + object );

                String title = object.getString("movie_title");
                String id = object.getString("movie_id");
                Short year = Short.parseShort(object.getString("release_year"));
                String director = object.getString("director");
                ArrayList<String> genres = new ArrayList<>(Arrays.asList(object.getString("genres").split(",")));
                ArrayList<String> actors = new ArrayList<>(Arrays.asList(object.getString("starNames").split(",")));
                Movie movie = new Movie(title, id, year, director, genres, actors);
                Movies.add(movie);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateListView() {
        MovieListViewAdapter adapter = new MovieListViewAdapter(this, Movies);
        ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Movie movie = Movies.get(position);
            @SuppressLint("DefaultLocale") String message = String.format("Clicked on position: %d, name: %s, %d", position, movie.getName(), movie.getYear());
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            Intent SingleMoviePage = new Intent(MovieListActivity.this, SingleMovieActivity.class);
            SingleMoviePage.putExtra("id", movie.getId());
        });
    }

}
