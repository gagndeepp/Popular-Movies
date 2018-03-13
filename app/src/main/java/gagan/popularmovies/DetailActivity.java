package gagan.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent input_intent = getIntent();
        Movie currentMovie = (Movie) input_intent.getSerializableExtra(this.getString(R.string.intent_extra_movie));
        TextView titleText = (TextView) findViewById(R.id.detail_title_view);
        TextView ratingText = (TextView) findViewById(R.id.detail_rating_view);
        TextView releaseDateText = (TextView) findViewById(R.id.detail_releasedate_view);
        TextView plotText = (TextView) findViewById(R.id.detail_plot_view);
        ImageView thumb_view = (ImageView) findViewById(R.id.movie_thumb_list_imageview);
        if (currentMovie.getThumbURL() == null)
            Picasso.with(this).load(R.drawable.no_poster).into(thumb_view);
        else
            Picasso.with(this).load(currentMovie.getThumbURL()).into(thumb_view);
        titleText.setText(currentMovie.getMovieTitle());
        ratingText.setText(this.getString(R.string.detail_activity_rating) + currentMovie.getRating());
        releaseDateText.setText(this.getString(R.string.detail_activoty_release) + currentMovie.getReleaseDate());
        plotText.setText(this.getString(R.string.detail_activoty_movie) + currentMovie.getMoviePlot());
    }
}
