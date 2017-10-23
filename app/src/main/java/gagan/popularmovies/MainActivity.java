package gagan.popularmovies;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import gagan.popularmovies.Utils.JSONHelper;
import gagan.popularmovies.Utils.NetworkHelper;

public class MainActivity extends AppCompatActivity {

    String TAG = "LOG TAG";
    MovieAdapter movieAdapter;
    RecyclerView recyclerView;
    ArrayList<Movie> movieArrayList;
    Boolean sortBoolean = true;
    ProgressBar progressBar;
    Toast mToast;
//   MenuItem popularity;
//   MenuItem rating;

    public class MovieASyncTask extends AsyncTask<String, Void, ArrayList<Movie>> {

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            movieAdapter.setMovieData(movies);
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Movie> doInBackground(String... params) {
            URL url = NetworkHelper.buildURL(sortBoolean);
            String http_data = null;
            ArrayList<Movie> returnMovieList = null;
            try {
                http_data = NetworkHelper.getHTTPData(url);
            } catch (IOException e) {
                Log.e(getLocalClassName(), "doInBackground: BAD RESPONSE FROM HTTP HELPER");
                e.printStackTrace();
            }
            try {
                returnMovieList = JSONHelper.getArrayListFromJSON(http_data);
            } catch (JSONException e) {
                Log.e(getLocalClassName(), "doInBackground: BAD RESPONSE FROM JSON HELPER");
                e.printStackTrace();
            }
            Log.d(TAG, "URL > " + url.toString());
            Log.d(TAG, "http_DATA" + http_data);

            return returnMovieList;
        }

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        recyclerView = (RecyclerView) findViewById(R.id.main_view_recycler);
//        popularity = (MenuItem) findViewById(R.id.recent_main_menu_item);
//        popularity.setChecked(true);
//        rating = (MenuItem) findViewById(R.id.rating_main_menu_item);
        
        new MovieASyncTask().execute("okay");
        movieArrayList = new ArrayList<>();
        movieAdapter = new MovieAdapter();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(movieAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.refresh_main_menu_item:
                new MovieASyncTask().execute("Place");
                if (mToast != null)
                    mToast.cancel();
                mToast = Toast.makeText(MainActivity.this, "Refresh!", Toast.LENGTH_SHORT);
                mToast.show();
                break;
            case R.id.recent_main_menu_item:
                sortBoolean = true;
//                popularity.setChecked(true);
//                rating.setChecked(false);

                new MovieASyncTask().execute("Place");
                if (mToast != null)
                    mToast.cancel();
                mToast = Toast.makeText(MainActivity.this, "Sorting By Popularity", Toast.LENGTH_SHORT);
                mToast.show();
                break;
            case R.id.rating_main_menu_item:
                sortBoolean = false;
//                rating.setChecked(true);
//                popularity.setChecked(false);
//                item.setChecked(true);
                new MovieASyncTask().execute("Place");
                if (mToast != null)
                    mToast.cancel();
                mToast = Toast.makeText(MainActivity.this, "Sorting By Rating", Toast.LENGTH_SHORT);
                mToast.show();
                break;
        }
        return true;
    }

}
