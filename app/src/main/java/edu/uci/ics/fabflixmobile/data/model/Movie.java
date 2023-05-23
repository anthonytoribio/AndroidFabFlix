package edu.uci.ics.fabflixmobile.data.model;

import java.util.ArrayList;

/**
 * Movie class that captures movie information for movies retrieved from MovieListActivity
 */
public class Movie {
    private final String name;
    private final short year;

    private final String id;

    private final String director;

    private final ArrayList<String> genres;

    private  final ArrayList<String> actors;


    public Movie(String name, String id, short year, String director, ArrayList<String> genres, ArrayList<String> actors) {
        this.name = name;
        this.id = id;
        this.year = year;
        this.director = director;
        this.genres = genres;
        this.actors = actors;

    }

    public String getName() {
        return name;
    }

    public short getYear() {
        return year;
    }

    public String getId() {
        return id;
    }


    public String getDirector() {
        return director;
    }

    public String getGenresString() {
        return String.join(",", genres);
    }

    public String getActorsString() {
        return String.join(",", actors);
    }

    public String get3GenresString() {
        Integer end = genres.size() >= 3? 3 : genres.size();

        return String.join(", ", genres.subList(0,end));
    }

    public String get3ActorsString() {
        return String.join(", ", actors.subList(0,3));
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public ArrayList<String> getActors() {
        return actors;
    }
}