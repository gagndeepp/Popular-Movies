package gagan.popularmovies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import gagan.popularmovies.Utils.JSONHelper;
import gagan.popularmovies.Utils.NetworkHelper;

public class MainActivity extends AppCompatActivity {

    public Context MainContext = this;
    private MovieAdapter movieAdapter;
    private RecyclerView recyclerView;
    private ArrayList<Movie> movieArrayList;
    private Boolean sortBoolean = true;
    private ProgressBar progressBar;
    private Toast mToast;
    private ConnectivityManager cm;
    private NetworkInfo netInfo;
    private TextView netInfoErrorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        recyclerView = (RecyclerView) findViewById(R.id.main_view_recycler);
        netInfoErrorView = (TextView) findViewById(R.id.network_error_indicator);
        cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        netInfo = cm.getActiveNetworkInfo();
        executeTask();
        movieArrayList = new ArrayList<>();
        movieAdapter = new MovieAdapter(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(movieAdapter);

    }

    private void executeTask() {
        netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            new MovieASyncTask().execute("okay");
            netInfoErrorView.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            netInfoErrorView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        }
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
                executeTask();
                if (mToast != null)
                    mToast.cancel();
                mToast = Toast.makeText(MainActivity.this, "Refresh!", Toast.LENGTH_SHORT);
                mToast.show();
                break;
            case R.id.recent_main_menu_item:
                sortBoolean = true;
                executeTask();
                if (mToast != null)
                    mToast.cancel();
                mToast = Toast.makeText(MainActivity.this, "Sorting By Popularity", Toast.LENGTH_SHORT);
                mToast.show();
                break;
            case R.id.rating_main_menu_item:
                sortBoolean = false;
                executeTask();
                if (mToast != null)
                    mToast.cancel();
                mToast = Toast.makeText(MainActivity.this, "Sorting By Rating", Toast.LENGTH_SHORT);
                mToast.show();
                break;
        }
        return true;
    }

    private class MovieASyncTask extends AsyncTask<String, Void, ArrayList<Movie>> {
        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            movieAdapter.setMovieData(movies);
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Movie> doInBackground(String... params) {
            URL url = NetworkHelper.buildURL(sortBoolean,MainActivity.this);
            String http_data = null;
            ArrayList<Movie> returnMovieList = null;
            try {
                http_data = NetworkHelper.getHTTPData(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                returnMovieList = JSONHelper.getArrayListFromJSON(http_data,MainActivity.this);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return returnMovieList;
        }

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        }
    }
}
