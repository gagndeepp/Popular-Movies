package gagan.popularmovies.Utils;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import static android.content.ContentValues.TAG;

/**
 * Created by Gagan on 8/28/2017.
 */

public class NetworkHelper {

    private static String BASE_URL_KEY = "https://api.themoviedb.org/3/discover/movie?api_key=8b992ad30917938aa657183a8a40af98";
    private static String BASE_THUMB_URL = "http://image.tmdb.org/t/p/";
    private static String THUMB_SIZE = "w342/";
    private static String PARAM_LANGUAGE = "language";
    private static String PARAM_REGION = "region";
    private static String PARAM_SORT = "sort_by";
    private static String PARAM_YEAR = "primary_release_year";

    public static URL buildURL(Boolean sortBy) {

        URL url = null;
        Uri uri;
        Uri.Builder builder  = new Uri.Builder();
        builder = Uri.parse(BASE_URL_KEY).buildUpon()
                .appendQueryParameter(PARAM_LANGUAGE, "en-US")
                .appendQueryParameter(PARAM_REGION, "US")
                .appendQueryParameter(PARAM_YEAR, String.valueOf(2017));
        if (sortBy)
            builder.appendQueryParameter(PARAM_SORT, "popularity.desc");
        else
            builder.appendQueryParameter(PARAM_SORT, "vote_average.desc");
        uri = builder.build();
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String buildImageURL(String end_path){
        String returnString;
        Uri uri;
        StringBuilder builder = new StringBuilder();
        builder.append(BASE_THUMB_URL)
                .append(THUMB_SIZE)
                .append(end_path);
        returnString = builder.toString();
        return returnString;
    }

    public static String getHTTPData(URL url) throws IOException {
        HttpURLConnection httpURLConnection = null;

        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "getHTTPData: IO EXCEPTION");
            return null;
        }
        if (httpURLConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            Log.e(TAG, "getHTTPData: BAD HTTP CONNECTION");
            return null;
        }

        InputStream is = null;
        try {
            assert httpURLConnection != null;
            is = httpURLConnection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(is);
        scanner.useDelimiter("\\A");
        if (scanner.hasNext()) {
            return scanner.next();
        } else
            return null;


    }

}
