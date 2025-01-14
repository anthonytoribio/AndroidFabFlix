package edu.uci.ics.fabflixmobile.ui.singlemovie;

import edu.uci.ics.fabflixmobile.R;
import edu.uci.ics.fabflixmobile.data.model.Movie;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SingleMovieViewAdapter extends ArrayAdapter<Movie> {
//    private final ArrayList<Movie> movies;
    private final ArrayList<Movie> movies;

    // View lookup cache
    private static class ViewHolder {
        TextView movieTitle;
        TextView movieYear;
        TextView movieRating;
        TextView movieDirector;
        TextView movieGenres;
        TextView movieStars;

    }

    public SingleMovieViewAdapter(Context context, ArrayList<Movie> movies) {
        super(context, R.layout.singlemovie_row, movies);
        this.movies = movies;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the movie item for this position
        Movie movie = movies.get(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.singlemovie_row, parent, false);
            viewHolder.movieTitle = convertView.findViewById(R.id.movieTitle);
            viewHolder.movieYear = convertView.findViewById(R.id.movieYear);
            viewHolder.movieRating = convertView.findViewById(R.id.movieRating);
            viewHolder.movieDirector = convertView.findViewById(R.id.movieDirector);
            viewHolder.movieGenres = convertView.findViewById(R.id.movieGenres);
            viewHolder.movieStars = convertView.findViewById(R.id.movieStars);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data from the data object via the viewHolder object
        // into the template view.
        viewHolder.movieTitle.setText(movie.getName());
        viewHolder.movieYear.setText(movie.getYear() + "");
        viewHolder.movieRating.setText(movie.getRating() + "");
        viewHolder.movieDirector.setText(movie.getDirector() + "");
        viewHolder.movieGenres.setText(movie.getGenresString() + "");
        viewHolder.movieStars.setText(movie.getActorsString() + "");
        // Return the completed view to render on screen
        return convertView;
    }
}
