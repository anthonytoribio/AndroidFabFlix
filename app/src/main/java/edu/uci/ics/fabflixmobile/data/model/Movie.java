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
}