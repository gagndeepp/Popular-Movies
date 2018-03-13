package gagan.popularmovies;

import java.io.Serializable;

import gagan.popularmovies.Utils.NetworkHelper;

public class Movie implements Serializable {
    private String movieTitle;
    private String moviePlot;
    private String rating;
    private String releaseDate;
    private String thumbURL;

    public Movie(String movieTitle, String moviePlot, String rating, String releaseDate, String thumbURL) {
        this.movieTitle = movieTitle;
        this.moviePlot = moviePlot;
        this.rating = rating;
        this.releaseDate = releaseDate;
        this.thumbURL = thumbURL;
    }

    String getMovieTitle() {
        return movieTitle;
    }

    String getMoviePlot() {
        return moviePlot;
    }

    String getRating() {
        return rating;
    }

    String getReleaseDate() {
        return releaseDate;
    }

    String getThumbURL() {
        if (thumbURL.contains("null"))
            return null;
        return NetworkHelper.buildImageURL(thumbURL);
    }
}
