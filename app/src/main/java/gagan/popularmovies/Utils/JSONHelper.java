package gagan.popularmovies.Utils;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import gagan.popularmovies.Movie;
import gagan.popularmovies.R;


public class JSONHelper {
    static Context main_context;
    public static ArrayList<Movie> getArrayListFromJSON(String jsonString,Context context) throws JSONException {
        main_context = context;
        ArrayList<Movie> returnList = new ArrayList<>();
        JSONObject baseObject = new JSONObject(jsonString);
        JSONArray resultsArray;
        if (baseObject.has(main_context.getString(R.string.json_string_param_result))) {
            resultsArray = baseObject.getJSONArray(main_context.getString(R.string.json_string_param_result));
        } else {
            return null;
        }
        for (int i = 0; i < resultsArray.length(); i++) {
            String movieTitle;
            String moviePlot;
            String rating;
            String releaseDate;
            String thumbURL;
            JSONObject currentMovie = resultsArray.getJSONObject(i);
            movieTitle = currentMovie.getString(main_context.getString(R.string.json_param_title));
            moviePlot = currentMovie.getString(main_context.getString(R.string.json_param_overview));
            rating = currentMovie.getString(main_context.getString(R.string.json_param_vote));
            releaseDate = currentMovie.getString(main_context.getString(R.string.json_param_release));
            thumbURL = currentMovie.getString(main_context.getString(R.string.json_param_thumb_path));
            Movie movieObject = new Movie(movieTitle, moviePlot, rating, releaseDate, thumbURL);
            returnList.add(movieObject);
        }
        return returnList;
    }

}
