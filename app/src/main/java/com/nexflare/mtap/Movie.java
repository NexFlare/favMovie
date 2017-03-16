package com.nexflare.mtap;

/**
 * Created by 15103068 on 27-01-2017.
 */

public class Movie {
    String Title;
    String Released;
    String Genre;
    String Director;
    String Actors;
    String Plot;
    String Poster;
    String imdbRating;
    String Runtime;
    String Response;

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
    }

    public String getRuntime() {
        return Runtime;
    }

    public void setRuntime(String runtime) {
        Runtime = runtime;
    }

    public Movie(String title, String released, String genre, String director, String actors, String plot, String poster, String imdbRating, String runtime) {
        Title = title;
        Released = released;
        Genre = genre;
        Director = director;
        Actors = actors;
        Plot = plot;
        Poster = poster;
        this.imdbRating = imdbRating;
        Runtime = runtime;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getReleased() {
        return Released;
    }

    public void setReleased(String released) {
        Released = released;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public String getDirector() {
        return Director;
    }

    public void setDirector(String director) {
        Director = director;
    }

    public String getActors() {
        return Actors;
    }

    public void setActors(String actors) {
        Actors = actors;
    }

    public String getPlot() {
        return Plot;
    }

    public void setPlot(String plot) {
        Plot = plot;
    }

    public String getPoster() {
        return Poster;
    }

    public void setPoster(String poster) {
        Poster = poster;
    }
}
