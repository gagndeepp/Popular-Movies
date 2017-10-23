package gagan.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    private TextView titleText;
    private TextView ratingText;
    private TextView releaseDateText;
    private TextView plotText;
    private TextView linkText;
    private ImageView thumb_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent input_intent = getIntent();
        Movie currentMovie = (Movie) input_intent.getSerializableExtra("movie");
        titleText = (TextView) findViewById(R.id.detail_title_view);
        ratingText = (TextView) findViewById(R.id.detail_rating_view);
        releaseDateText = (TextView) findViewById(R.id.detail_releasedate_view);
        plotText = (TextView) findViewById(R.id.detail_plot_view);
        thumb_view = (ImageView) findViewById(R.id.movie_thumb_list_imageview);
        if (currentMovie.getThumbURL() == null)
            Picasso.with(this).load(R.drawable.no_poster).into(thumb_view);
        else
            Picasso.with(this).load(currentMovie.getThumbURL()).into(thumb_view);
        titleText.setText(currentMovie.getMovieTitle());
        ratingText.setText("Rating :" + currentMovie.getRating());
        releaseDateText.setText("Release Date:" + currentMovie.getReleaseDate());
        plotText.setText("Movie Plot:\n" + currentMovie.getMoviePlot());
    }
}
