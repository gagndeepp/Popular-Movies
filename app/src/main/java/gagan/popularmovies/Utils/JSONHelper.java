package gagan.popularmovies.Utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import gagan.popularmovies.Movie;

import static android.content.ContentValues.TAG;

/**
 * Created by Gagan on 8/29/2017.
 */

public class JSONHelper {


    public static ArrayList<Movie> getArrayListFromJSON(String jsonString) throws JSONException {
        ArrayList<Movie> returnList = new ArrayList<>();
        JSONObject baseObject = new JSONObject(jsonString);
        JSONArray resultsArray = null;
        if (baseObject.has("results")) {
            resultsArray = baseObject.getJSONArray("results");
            Log.d(TAG, "getArrayListFromJSON: " + resultsArray.toString());
        } else {
            Log.d(TAG, "getArrayListFromJSON: Bad Response No Results Array");
            return null;
        }
        for (int i = 0; i < resultsArray.length(); i++) {
            String movieTitle;
            String moviePlot;
            String rating;
            String releaseDate;
            String thumbURL;
            JSONObject currentMovie = resultsArray.getJSONObject(i);
            movieTitle = currentMovie.getString("title");
            moviePlot = currentMovie.getString("overview");
            rating = currentMovie.getString("vote_average");
            releaseDate = currentMovie.getString("release_date");
            thumbURL = currentMovie.getString("poster_path");
            Movie movieObject = new Movie(movieTitle, moviePlot, rating, releaseDate, thumbURL);
            returnList.add(movieObject);
        }

        return returnList;
    }

}
