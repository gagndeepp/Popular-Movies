package gagan.popularmovies;

import java.io.Serializable;

import gagan.popularmovies.Utils.NetworkHelper;

/**
 * Created by Gagan on 8/28/2017.
 */

public class Movie implements Serializable{
    private String movieTitle;
    private String moviePlot;
    private String rating;
    private String releaseDate;
    private String movieURL;
    private String thumbURL;
//    private int sample_thumb;
//
//    public int getSample_thumb() {
//        return sample_thumb;
//    }

    public Movie(String movieTitle, String moviePlot, String rating, String releaseDate, String thumbURL) {
        this.movieTitle = movieTitle;
        this.moviePlot = moviePlot;
        this.rating = rating;
        this.releaseDate = releaseDate;
        this.thumbURL = thumbURL;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getMoviePlot() {
        return moviePlot;
    }

    public String getRating() {
        return rating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getMovieURL() {
        return movieURL;
    }

    public String getThumbURL()
    {
        if(thumbURL.contains("null"))
            return null;
        return NetworkHelper.buildImageURL(thumbURL);
    }
}
