package gagan.popularmovies.Utils;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import gagan.popularmovies.MainActivity;
import gagan.popularmovies.R;

public class NetworkHelper extends MainActivity {
    static Context main_context;
    public static URL buildURL(Boolean sortBy, Context context) {
        main_context = context;
        URL url = null;
        Uri uri;
        Uri.Builder builder = new Uri.Builder();
        builder = Uri.parse(main_context.getString(R.string.BASE_URL_KEY)).buildUpon();
        if (sortBy)
            builder.appendEncodedPath(main_context.getString(R.string.url_sortby_popular));
        else
            builder.appendEncodedPath(main_context.getString(R.string.url_sortby_top_rated));
        builder.appendQueryParameter(main_context.getString(R.string.network_helper_api_key),main_context.getString(R.string.API_KEY));
        uri = builder.build();
        Log.d("DEBUG",uri.toString());
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String buildImageURL(String end_path) {
        String returnString;
        returnString = main_context.getString(R.string.BASE_THUMB_URL) +
                main_context.getString(R.string.THUMB_SIZE) +
                end_path;
        return returnString;
    }


    public static String getHTTPData(URL url) throws IOException {
        HttpURLConnection httpURLConnection;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        if (httpURLConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            return null;
        }
        InputStream is = null;
        try {
            is = httpURLConnection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert is != null;
        Scanner scanner = new Scanner(is);
        scanner.useDelimiter("\\A");
        if (scanner.hasNext()) {
            return scanner.next();
        } else
            return null;
    }
}
