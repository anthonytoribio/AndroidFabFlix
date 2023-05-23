package edu.uci.ics.fabflixmobile.ui.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import edu.uci.ics.fabflixmobile.R;
import edu.uci.ics.fabflixmobile.databinding.MainBinding;
import android.util.Log;
import android.content.Intent;
import edu.uci.ics.fabflixmobile.ui.movielist.MovieListActivity;

public class MainActivity extends AppCompatActivity {

    private EditText searchText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        searchText = findViewById(R.id.searchText);

        Button searchButton = (Button) findViewById(R.id.searchButton);

        Log.d("MAIN", "INSIDE CREATE");
        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                search();
            }
        });


    }

    public void search(){
        Log.d("MAIN", "THE SEARCH FIELD IS: " + searchText.getText().toString());
        Log.d("MAIN", "HELLO");

        //START NEW MovieListActivity with the searchText passed to the intent
        Intent MovieListPage = new Intent(MainActivity.this, MovieListActivity.class);
        MovieListPage.putExtra("payload", searchText.getText().toString());

//        finish();
        startActivity(MovieListPage);

    }
}
